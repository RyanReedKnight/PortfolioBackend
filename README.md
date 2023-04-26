# PortfolioBackend
This is a Spring Boot 3 backend for a website that alows the owner to share their photos. Photos are meant to be uploaded by the authorized user 
via <a href="https://github.com/RyanReedKnight/portfolio-admin">the portfolio admin page</a>, and can be viewed by anyone
via <a href="https://github.com/RyanReedKnight/Portfolio">the portfolio webpage.</a>
  
  For each photo that is saved, the file bytes are saved to a designated directory, and other information pertaining to the photo 
 (title, description, location) are saved in a PostgreSQL database.

# Deployment Instructions
 The application must be configured with a front end or front ends to make requests to upload and view photos (I have these separate), 
a PostgreSQL database, a storage location for the photos, a JWT secret, and an admin username and password. 
These are all defined with the following environment variables.  
  
  * RDS_URL - the url of the PostgreSQL database.
  * RDS_USERNAME - the username associated with the database.
  * RDS_PASSWORD - the password associated with the database.
  * JWT_SECRET - the JWT secret.
  * PHOTO_STORAGE_LOCATION - a location mounted onto the host device to store photos.
  * ADMIN_USERNAME - username for owner of the website.
  * ADMIN_PASSWORD - password for the owner of the account.
  * ADMIN_PORTAL - location where the admin portal is hosted.
  * CLIENT - location where the main webpage is hosted.

 The host device must have the Java Runtime Environment for Java 17, and the device that builds the package must have maven installed.
The package is built using the command "mvn clean package," note that on if the version of maven is too old, it will not build for Java 17. 
When attempting to build in a Linux environment, I found that the maven package availible in the distribution repositories 
of Debian 11 and Ubuntu 20.04 were too old, however Ubuntu 22.04 worked as expected. 
   Once there is a jar file, the file is on the host device, and the previously mentioned environment variables have been defined, 
run the command 
```
 nohup java -DRDS_USERNAME=$RDS_USERNAME \
-DRDS_PASSWORD=$RDS_PASSWORD \
-DRDS_URL=$RDS_URL \
-DPHOTO_STORAGE_LOCATION=$PHOTO_STORAGE_LOCATION \
-DJWT_SECRET=$JWT_SECRET \
-DADMIN_USERNAME=$ADMIN_USERNAME \
-DADMIN_PASSWORD=$ADMIN_PASSWORD \
-DADMIN_PORTAL=$ADMIN_PORTAL \
-DCLIENT=$CLIENT \
-jar ~/PortfolioBackend-<version>-SNAPSHOT.jar &
``` 
to start the application.

# Deployed site
  A deployment of this site can be seen <a href="http://photos-and-such.s3-website-us-west-2.amazonaws.com/">here.</a>
Both the admin portal and the main page are React applications statically hosted in AWS S3 buckets. The backend is run out of an EC2 instance, 
has an EBS drive mounted for the photos, and is connected to an AWS RDS instance for the database.
 
# Future work
  As of now most of the work I have planed are features and aesthetic improvments on the front end, I would like to add photo albums, 
and enable users to filter by album. I may rewrite the application in another framework as a means by which to practice using said framework. 
I also want to set up a code pipeline for this site for convinience's sake
