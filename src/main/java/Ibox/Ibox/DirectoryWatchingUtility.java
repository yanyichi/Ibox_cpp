package Ibox.Ibox;

import com.amazonaws.services.s3.AmazonS3;
import io.methvin.watcher.DirectoryWatcher;

import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

public class DirectoryWatchingUtility {

    private final Path directoryToWatch;
    private final DirectoryWatcher watcher;
    private IboxDriveRemote iboxDriveRemote = new IboxDriveRemote();


    public DirectoryWatchingUtility(Path directoryToWatch,AmazonS3 s3, String bucketName) throws IOException {
        this.directoryToWatch = directoryToWatch;
        this.watcher = DirectoryWatcher.builder()
                .path(directoryToWatch)
                .listener(event -> {
                    switch (event.eventType()) {
                        case CREATE: {
                            iboxDriveRemote.uploadObject(
                                    s3, bucketName,
                                    event.path().getFileName().toString(),
                                    event.path().toAbsolutePath().toString()
                            );
                            System.out.println("Found a new file.");
                            break;
                        }
                        case MODIFY:{
                            iboxDriveRemote.uploadObject(
                                    s3, bucketName,
                                    event.path().getFileName().toString(),
                                    event.path().toAbsolutePath().toString()
                            );
                            System.out.println("Modify target file");
                            break;
                        }
                        case DELETE:{
                            iboxDriveRemote.deleteObject(s3,bucketName,event.path().getFileName().toString());
                            System.out.println("Delete target file");
                            break;
                        }
                    }
                })
                // .fileHashing(false) // defaults to true
                // .logger(logger) // defaults to LoggerFactory.getLogger(DirectoryWatcher.class)
                // .watchService(watchService) // defaults based on OS to either JVM WatchService or the JNA macOS WatchService
                .build();
    }

    public void stopWatching() throws IOException {
        watcher.close();
    }

    public CompletableFuture<Void> watch() {
        // you can also use watcher.watch() to block the current thread
        return watcher.watchAsync();
    }
}
