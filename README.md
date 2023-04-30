# photo-portfolio-backend
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
The package is built using the command "mvn clean package," note that if the version of maven is too old, it will not build for Java 17. 
When attempting to build in a Linux environment, I found that the maven package availible in the distribution repositories 
of Debian 11 and Ubuntu 20.04 were too old, however Ubuntu 22.04 worked as expected. 
   Once there is a jar file, the file is on the host device, and the previously mentioned environment variables have been defined, 
run the following command to start the application. 
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
-jar ./PortfolioBackend-*-SNAPSHOT.jar &
``` 
# Deployed site
  The deployed site can be seen <a href=<a href="http://ec2-35-89-191-219.us-west-2.compute.amazonaws.com/">here</a>">here.</a>
Both the admin portal is hosted in an AWS S3 bucket, while the main webpage 
is hosted on an EC2 instance and served by Nginx. The backend is run out of another EC2 instance, 
has an EBS drive mounted for the photos, and is connected to an AWS RDS instance for the database.
 
# Future work
  * Incorporate logging into the application.
  * Add an album feature where photos are assigned an album which users can filter by.

# Links
  * <a href="https://github.com/RyanReedKnight/Portfolio">Application front-end repository</a>
  * <a href="https://github.com/RyanReedKnight/portfolio-admin">Portfolio admin page repository</a>
  * <a href="http://photos-and-such.s3-website-us-west-2.amazonaws.com/">The website</a>
