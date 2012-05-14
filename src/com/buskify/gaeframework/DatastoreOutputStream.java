package com.buskify.gaeframework;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

import org.apache.wicket.util.lang.Bytes;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

/**
 * @author uudashr
 *
 */
public class DatastoreOutputStream extends OutputStream {
    public static final int DATA_CHUNK_SIZE = (int)(Bytes.megabytes(1).bytes() - Bytes.kilobytes(10).bytes());
    private DatastoreService dsService = DatastoreServiceFactory.getDatastoreService();
    private boolean closed = false;
    private ByteBuffer buffer = ByteBuffer.allocate(DATA_CHUNK_SIZE);
    private int chunkIndex = 0;
    int totalSize = 0;
    private final String fileName;
    
    private Entity fileEntity;
    
    public DatastoreOutputStream(String fileName) {
        this.fileName = fileName;
    }
    
    
    @Override
    public void write(int b) throws IOException {
        checkClosed();
        if (fileEntity == null) {
            fileEntity = new Entity("File", fileName);
            fileEntity.setProperty("name", fileName);
            dsService.put(fileEntity);
        }
        
        if (buffer.hasRemaining()) {
            buffer.put((byte)b);
            totalSize++;
        } else {
            flushCompleted();
        }
    }
    
    private void flushCompleted() {
        if (fileEntity != null) {
            int length = buffer.position();
            Entity chunkEntity = new Entity("ChunkFile", "cf" + chunkIndex, fileEntity.getKey());
            chunkEntity.setProperty("index", chunkIndex);
            
            byte[] data = new byte[length]; buffer.flip(); buffer.get(data);
            chunkEntity.setUnindexedProperty("data", new Blob(data));
            chunkEntity.setUnindexedProperty("length", length);
            dsService.put(chunkEntity);
            
            
            buffer.clear();
            
            // update size
            fileEntity.setProperty("size", new Long(totalSize));
            dsService.put(fileEntity);
            chunkIndex++;
        } else {
        }
    }
    
    private void checkClosed() throws IOException {
        if (closed) {
            throw new IOException("Resource already closed");
        }
    }
    
    @Override
    public void close() throws IOException {
        checkClosed();
        if (buffer.hasRemaining()) {
            flushCompleted();
        }
        closed = true;
        buffer = null;
    }
    
    @Override
    protected void finalize() throws Throwable {
        buffer = null;
    }
}
