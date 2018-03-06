//<start id="method_addSpitterFromForm"/> 
@RequestMapping(method=RequestMethod.POST)
public String addSpitterFromForm(@Valid Spitter spitter, 
                                 BindingResult bindingResult) {
  if(bindingResult.hasErrors()) { //<co id="co_checkForErrors"/> 
    return "spitters/edit";
  }
  
  spitterService.saveSpitter(spitter); //<co id="co_saveSpitter"/>
  
  return "redirect:/spitters/" + spitter.getUsername(); //<co id="co_redirectAfterPost"/>
}
//<end id="method_addSpitterFromForm"/> 


//<start id="method_addSpitterFromForm_ImageUpload"/> 
@RequestMapping(method=RequestMethod.POST)
public String addSpitterFromForm(@Valid Spitter spitter, 
    BindingResult bindingResult,
    @RequestParam(value="image", required=false) //<co id="co_multipartField"/> 
        MultipartFile image) {
  if(bindingResult.hasErrors()) {
    return "spitters/edit";
  } 
  
  spitterService.saveSpitter(spitter);
  
  try {
    if(!image.isEmpty()) {
      validateImage(image);   //<co id="co_validateImage"/> 
      
      saveImage(spitter.getId() + ".jpg", image); // <co id="co_saveImage"/> 
    }
  } catch (ImageUploadException e) {
    bindingResult.reject(e.getMessage());
    return "spitters/edit";
  }

  return "redirect:/spitters/" + spitter.getUsername();
}
//<end id="method_addSpitterFromForm_ImageUpload"/>



//<start id="method_saveImage_FileSystem"/> 
private void saveImage(String filename, MultipartFile image) 
      throws ImageUploadException {
  try {
    File file = new File(webRootPath + "/resources/" + filename);
    FileUtils.writeByteArrayToFile(file, image.getBytes());
  } catch (IOException e) {
    throw new ImageUploadException("Unable to save image", e);
  }
}
//<end id="method_saveImage_FileSystem"/> 


//<start id="method_validateImage"/> 
private void validateImage(MultipartFile image) {
  if(!image.getContentType().equals("image/jpeg")) {
    throw new ImageUploadException("Only JPG images accepted");
  }
}
//<end id="method_validateImage"/> 


//<start id="method_saveImage_AmazonS3"/> 
private void saveImage(String filename, MultipartFile image) 
      throws ImageUploadException {

  try {
    AWSCredentials awsCredentials = 
      new AWSCredentials(s3AccessKey, s3SecretKey);      
    S3Service s3 = new RestS3Service(awsCredentials);//<co id="co_s3service"/> 
    
    S3Bucket imageBucket = s3.getBucket("spitterImages");
    S3Object imageObject = new S3Object(filename);//<co id="co_s3objects"/>
    
    imageObject.setDataInputStream(
            new ByteArrayInputStream(image.getBytes()));
    imageObject.setContentLength(image.getBytes().length);
    imageObject.setContentType("image/jpeg");//<co id="co_setBytes"/> 
    
    AccessControlList acl = new AccessControlList();//<co id="co_setPermissions"/> 
    acl.setOwner(imageBucket.getOwner());
    acl.grantPermission(GroupGrantee.ALL_USERS, 
            Permission.PERMISSION_READ);
    imageObject.setAcl(acl);
    
    s3.putObject(imageBucket, imageObject); //<co id="co_putImage"/> 
  } catch (Exception e) {
    throw new ImageUploadException("Unable to save image", e);
  }
}
//<end id="method_saveImage_AmazonS3"/> 
