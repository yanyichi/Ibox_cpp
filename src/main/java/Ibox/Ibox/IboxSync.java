package Ibox.Ibox;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;

import java.io.IOException;
import java.nio.file.Paths;

public class IboxSync {

    private static final String resourcesDir = System.getProperty("user.dir")+"/src/main/resources";
    private static final String IboxDir = "/demoDrive/";
    private static final String bucketName = "ibox-bucket";

    public boolean start() throws IOException {
        AmazonS3 s3 = new AmazonS3Client();
        Region usWest2 = Region.getRegion(Regions.US_WEST_2);
        s3.setRegion(usWest2);

        try{
            s3.getBucketPolicy(bucketName);
        }catch (AmazonServiceException e){
            if(e.getStatusCode()==404){ s3.createBucket(bucketName); }
        }
        DirectoryWatchingUtility directoryWatchingUtility =
                new DirectoryWatchingUtility(Paths.get(resourcesDir+IboxDir), s3, bucketName);
        directoryWatchingUtility.watch();

        return true;
    }

}
