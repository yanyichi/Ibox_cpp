package Ibox.Ibox;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AnonymousAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import io.findify.s3mock.S3Mock;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class IboxApplicationTests {

	IboxApplication iboxApplication = new IboxApplication();
	IboxDriveLocal iboxDriveLocal = new IboxDriveLocal();
	IboxDriveRemote iboxDriveRemote = new IboxDriveRemote();
	IboxSync iboxSync = new IboxSync();

	private static final String testResourcesDir = System.getProperty("user.dir")+"/src/test/resources";
	private static final String testIboxDir = "/testDemoDrive/";
	private static final String bucketName = "ibox-bucket-test";

	private static final AmazonS3 s3 = new AmazonS3Client();



	private S3Mock s3Mock;
	private String endpoint;

//	@Before
//	public void setUp() throws Exception {
//
//		endpoint = "http://127.0.0.1:8001";
//
//	}
//
//	@After
//	public void tearDown() throws Exception {
//		try  {
//			s3Mock.stop();
//		}catch (Exception e){
//		}
//	}

	@Test
	public void testDirectoryWatchingUtilityMock() throws IOException, InterruptedException {
		try {
			s3Mock = S3Mock.create(8003, testResourcesDir);
			s3Mock.start();
			AmazonS3 mockS3Client = new AmazonS3Client(new AnonymousAWSCredentials());
			mockS3Client.setEndpoint("http://127.0.0.1:8003");
			mockS3Client.createBucket(bucketName);
			DirectoryWatchingUtility directoryWatchingUtility =
					new DirectoryWatchingUtility(Paths.get(testResourcesDir+testIboxDir), mockS3Client, bucketName);

			directoryWatchingUtility.watch();
			File file2 = new File(testResourcesDir+testIboxDir+"testWacherMock.txt");
			file2.getParentFile().mkdir();
			file2.createNewFile();
			sleep(3000);

			assertEquals(true,iboxDriveRemote.getObject(mockS3Client,bucketName,"testWacherMock.txt").getKey().equals("testWacherMock.txt"));
			file2.delete();
			sleep(3000);
			mockS3Client.deleteBucket(bucketName);
			sleep(3000);
			directoryWatchingUtility.stopWatching();
		} catch (AmazonServiceException ase) {
			System.out.println("Error Message: " + ase.getMessage());
		}
	}


	@Test
	public void contextLoads() {
	}

	@Test
	public void listFilesForFolderUnitTest(){
		HashMap<String,String> map = new HashMap<>();
		map.put("testDemoDrive/test1",testResourcesDir+testIboxDir+ "testDemoDrive/test1");
		map.put("testDemoDrive/test2",testResourcesDir+testIboxDir+ "testDemoDrive/test2");
		map.put("testDemoDrive/subFolder/test3",testResourcesDir+testIboxDir+ "testDemoDrive/subFolder/test3");
		System.out.println(testResourcesDir+testIboxDir);
		assertEquals(map.size(),iboxDriveLocal.listFilesForFolder(new File(testResourcesDir+testIboxDir)).size());
	}

	@Test
	public void uploadMockUnitTest(){
		try {
			s3Mock = S3Mock.create(8002, testResourcesDir);
			s3Mock.start();
			AmazonS3 mockS3Client = new AmazonS3Client(new AnonymousAWSCredentials());
			mockS3Client.setEndpoint("http://127.0.0.1:8002");
			mockS3Client.createBucket(bucketName);
			iboxDriveRemote.uploadObject(mockS3Client,bucketName,"test1", testResourcesDir+testIboxDir+"test1");
			assertEquals(true,iboxDriveRemote.getObject(mockS3Client,bucketName,"test1").getKey().equals("test1"));
			mockS3Client.deleteObject(bucketName, "test1");
			mockS3Client.deleteBucket(bucketName);
		} catch (AmazonServiceException ase) { throw  ase; }
	}




	@Test
	public void deleteMockUnitTest(){
		try {
			s3Mock = S3Mock.create(8001, testResourcesDir);
			s3Mock.start();
			AmazonS3 mockS3Client = new AmazonS3Client(new AnonymousAWSCredentials());
			mockS3Client.setEndpoint("http://127.0.0.1:8001");
			mockS3Client.createBucket(bucketName);
			iboxDriveRemote.uploadObject(mockS3Client,bucketName,"test1", testResourcesDir+testIboxDir+"test1");
			assertEquals(true,iboxDriveRemote.deleteObject(mockS3Client,bucketName,"test1"));
			mockS3Client.deleteBucket(bucketName);
		} catch (AmazonServiceException ase) { throw  ase; }
	}

	@Test
	public void uploadIntegrationTest(){
		try {
			s3.createBucket(bucketName);
			iboxDriveRemote.uploadObject(s3,bucketName,"test1", testResourcesDir+testIboxDir+"test1");
			assertEquals(true,iboxDriveRemote.getObject(s3,bucketName,"test1").getKey().equals("test1"));
			s3.deleteObject(bucketName, "test1");
			s3.deleteBucket(bucketName);
		} catch (AmazonServiceException ase) {
			System.out.println("Error Message: " + ase.getMessage());
		}
	}



	@Test
	public void testMain() throws IOException {
		IboxApplication.main(new String[] {"arg1", "arg2", "arg3"});
	}

	@Test
	public void testIboxSync() throws IOException {
		assertEquals(true,iboxSync.start());
	}

//	@Test
//	public void testDirectoryWatchingUtility() throws IOException, InterruptedException {
//		try {
//			s3.createBucket(bucketName);
//
//			DirectoryWatchingUtility directoryWatchingUtility =
//					new DirectoryWatchingUtility(Paths.get(testResourcesDir+testIboxDir), s3, bucketName);
//
//			directoryWatchingUtility.watch();
//
//			File file1 = new File(testResourcesDir+testIboxDir+"testWacher.txt");
//			file1.getParentFile().mkdir();
//			file1.createNewFile();
//			sleep(3000);
//			assertEquals(true,iboxDriveRemote.getObject(s3,bucketName,"testWacher.txt").getKey().equals("testWacher.txt"));
//			file1.delete();
//			sleep(3000);
//			s3.deleteBucket(bucketName);
//			sleep(3000);
//			directoryWatchingUtility.stopWatching();
//		} catch (AmazonServiceException ase) {
//			System.out.println("Error Message: " + ase.getMessage());
//		}
//	}



}
