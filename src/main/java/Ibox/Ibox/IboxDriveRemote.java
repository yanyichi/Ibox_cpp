package Ibox.Ibox;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

import java.io.File;


public class IboxDriveRemote {

    public boolean uploadObject(AmazonS3 s3, String bucketName, String fileName, String filePath) {
        return s3.putObject(new PutObjectRequest(bucketName, fileName, new File(filePath))).getContentMd5().length()==24;
    }

    public S3Object getObject(AmazonS3 s3, String bucketName, String key){
        try{
           if( s3.getObject(bucketName,key)!=null){
               return s3.getObject(bucketName,key);
            }
            return null;
        }catch (Exception e){throw e;}

    }

    public boolean deleteObject(AmazonS3 s3,String bucketName, String key){
        s3.deleteObject(bucketName,key);
        return true;
    }

}



