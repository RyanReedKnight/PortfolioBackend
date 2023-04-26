# PortfolioBackend
 <p>This is a backend for a website that alows the owner to share their photos. Photos are meant to be uploaded by the authorized user 
   via <a href="https://github.com/RyanReedKnight/portfolio-admin">the portfolio admin page</a>, and can be viewed by anyone
   via <a href="https://github.com/RyanReedKnight/Portfolio">the portfolio webpage.</a></p>
  
 <p> For each photo that is saved, the file bytes are saved to a designated directory, and other information pertaining to the photo (title, description, location) are saved in a PostgreSQL database.<p>

# Deployment Instructions
 <p> The application must be configured with a front end or front ends to make requests to upload and view photos (I have these separate), a PostgreSQL database, a storage location for the photos, a JWT secret, and an admin username and password. These are all defined with the following environment variables.
  <ul>
    <li> RDS_URL - the url of the PostgreSQL database.</li>
    <li> RDS_USERNAME - the username associated with the database </li>
    <li> RDS_PASSWORD - the password associated with the database </li>
    <li> JWT_SECRET - the JWT secret </li>
    <li> PHOTO_STORAGE_LOCATION - a location mounted onto the host device to store photos. </li>
    <li> ADMIN_USERNAME - username for owner of the website. </li>
    <li> ADMIN_PASSWORD - password for the owner of the account. </li>
    <li> ADMIN_PORTAL - location where the admin portal is hosted. </li>
    <li> CLIENT - location where the main webpage is hosted. </li>
  <ul>
 </p>
# Deployed site
<p>   My deployment of this site can be seen <a href="http://photos-and-such.s3-website-us-west-2.amazonaws.com/">here.</a>
Both the admin portal and the main page or React applications statically hosted in AWS S3 buckets. The backend is run out of an EC2 instance, has an EBS drive mounted for the photos, and is connected to an AWS RDS instance for the database.</p>
 
# Future work
<p> As of now most of the work I have planed are features and aesthetic improvments on the front end, I would like to add photo albums, and enable users to filter by album. I may rewrite the application in another framework as a means by which to practice using said framework.</p>
