package com.buskify.gaeframework;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Iterator;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

/**
 * @author uudashr
 *
 */
public class DatastoreInputStream extends InputStream {
    private final DatastoreService dsService = DatastoreServiceFactory.getDatastoreService();
    
    private boolean closed;
    private final Iterator<Entity> entities;
    private ByteBuffer buffer;
    private String fileName;
    private int counter = 0;
    
    public DatastoreInputStream(String fileName) throws FileNotFoundException {
        this.fileName = fileName;
        Key fileKey = KeyFactory.createKey("File", fileName);
        try {
            // make sure file is exists
            dsService.get(fileKey);
        } catch (EntityNotFoundException e) {
            throw new FileNotFoundException("Cannot find file with key '" + fileKey + "': " + e.getMessage());
        }
        Query query = new Query("ChunkFile", fileKey);
        query.addSort("index");
        PreparedQuery pQuery = dsService.prepare(query);
        entities = pQuery.asIterator();
    }
    
    @Override
    public int read() throws IOException {
        checkClosed();
        if (buffer == null || !buffer.hasRemaining()) {
            if (entities.hasNext()) {
                Entity entity = entities.next();
                Blob blobData = (Blob)entity.getProperty("data");
                buffer = ByteBuffer.wrap(blobData.getBytes());
                counter++;
            } else {
                return -1;
            }
        }
        
        // TODO uudashr: the might me empty data
        int b = 0xff & buffer.get();
        if (b < 0) {
            // logger.debug("Got " + b);
        }
        return b;
    }
    
    private void checkClosed() throws IOException {
        if (closed) {
            throw new IOException("Resource already closed");
        }
    }
    
    @Override
    public void close() throws IOException {
        checkClosed();
        closed = true;
        buffer = null;
    }
    
    @Override
    protected void finalize() throws Throwable {
        buffer = null;
    }
    
    @Override
    public String toString() {
        return "DatastoreInputStream for " + fileName;
    }
}
