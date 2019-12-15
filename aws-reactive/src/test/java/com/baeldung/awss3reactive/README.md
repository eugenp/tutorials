# AWS S3 with Java - Reactive Support

This README describe how to setup a test environment for running LiveTests available in this package.

## Running LiveTests with AWS S3 Service

 1. If you don't have an AWS Account, create one. You'll need a credit card, but you should be able to
    run all your tests using the free tier.    
 2. In the AWS Console, select "Services > Storage > S3"
 3. In the S3 side menu, select "Buckets" then click on "Create Bucket"
 4. Follow the Bucket creation wizard instructions. You can use the suggested default values for the purposes of running
    the tests
 5. On the top menu, click on your account name and select "My Security Credentials"
 6. Expand the "Access Keys" section
 7. Click on "Create New Key"
 8. Click on "Download Key File" or just copy&paste the values for Access Key ID and Secret Access Key
 9. Add the generated access key & secret to the file src/main/resources/application.yml
 10. Edit the bucket name and region name in src/main/resources/application.yml to match the one we created.
 
Note: Do *NOT* use production credentials for testing! Also, adding credentials to files that end up in version control is generally a bad idea. Instead, you can use (other available methods)[https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/credentials.html] to authenticate with AWS.

## Running LiveTests with MinIO

 1. Install MinIO Server in your workstation using the appropriate package for your OS. [Download site](https://min.io/download)
 2. Create a folder to use as root for buckets
 3. Start MinIO server: `minio server <root folder>`
 4. MinIO will print the access and secret codes required to interact with the service. Copy those values to src/main/resources/application-minio.yml
 5. Start a browser and point to [http://localhost:9000]. Use the same access & secret values to access MinIO's control panel
 6. Click on the red "+" button located at the right lower corner, then click on "Create Bucket".
 7. Give the bucket some reasonable name and hit *enter*. Edit application-minio.yml and edit the *aws.s3.bucketName* property value 
    to match the name you've given
    
Note: This procedure assumes a MinIO server runnning on the same machine where LiveTests will run. If this is not the case,
just edit the *aws.s3.endpoint* property.


 
    

  