package com.habuma.spitter.mvc;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.validation.Valid;

import org.jets3t.service.S3Service;
import org.jets3t.service.acl.AccessControlList;
import org.jets3t.service.acl.GroupGrantee;
import org.jets3t.service.acl.Permission;
import org.jets3t.service.impl.rest.httpclient.RestS3Service;
import org.jets3t.service.model.S3Bucket;
import org.jets3t.service.model.S3Object;
import org.jets3t.service.security.AWSCredentials;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import com.habuma.spitter.domain.Spitter;
import com.habuma.spitter.domain.Spittle;
import com.habuma.spitter.service.SpitterService;

@Controller
@RequestMapping("/spitters")
public class SpitterController {
  private final SpitterService spitterService;
  
  @Value("#{s3Properties['s3.accessKey']}")
  private String s3AccessKey;
  @Value("#{s3Properties['s3.secretKey']}")
  private String s3SecretKey;
  
  @Inject
  public SpitterController(SpitterService spitterService) {
    this.spitterService = spitterService;
  }

  @RequestMapping(method=RequestMethod.GET)
  public String listSpitters(
          @RequestParam(value="page", defaultValue="1") int page,
          @RequestParam(value="perPage", defaultValue="10") int perPage,
          Map<String, Object> model) {
    model.put("spitters", spitterService.getAllSpitters());
    return "spitters/list";
  }
  
  //<start id="method_new_spitter"/> 
  @RequestMapping(method=RequestMethod.GET, params="new")
  public String createSpitterProfile(Model model) {
    model.addAttribute(new Spitter());
    return "spitters/edit";
  }
  //<end id="method_new_spitter"/> 
  
  //<start id="method_addSpitterFromForm"/> 
  @RequestMapping(method=RequestMethod.POST)
  public String addSpitterFromForm(@Valid Spitter spitter, 
      BindingResult bindingResult,
      @RequestParam(value="image", required=false) MultipartFile image) {
    
    if(bindingResult.hasErrors()) {
      return "spitters/edit";
    } 
    
    spitterService.saveSpitter(spitter);
    
    try {
      if(!image.isEmpty()) {
        validateImage(image);
        saveImage(spitter.getId() + ".jpg", image);
      }
    } catch (ImageUploadException e) {
      bindingResult.reject(e.getMessage());
      return "spitters/edit";
    }

    return "redirect:/spitters/" + spitter.getUsername();
  }  
  //<end id="method_addSpitterFromForm"/> 
 
  private void saveImage(String filename, MultipartFile image) 
        throws ImageUploadException {
    
    try {
      AWSCredentials awsCredentials = 
        new AWSCredentials(s3AccessKey, s3SecretKey);      
      S3Service s3 = new RestS3Service(awsCredentials);
      
      S3Bucket imageBucket = s3.getBucket("spitterImages");
      S3Object imageObject = new S3Object(filename);
      
      imageObject.setDataInputStream(
              new ByteArrayInputStream(image.getBytes()));
      imageObject.setContentLength(image.getBytes().length);
      imageObject.setContentType("image/jpeg");
      
      AccessControlList acl = new AccessControlList();
      acl.setOwner(imageBucket.getOwner());
      acl.grantPermission(GroupGrantee.ALL_USERS, 
              Permission.PERMISSION_READ);
      imageObject.setAcl(acl);
      s3.putObject(imageBucket, imageObject); 
    } catch (Exception e) {
      throw new ImageUploadException("Unable to save image", e);
    }
  }

  private void validateImage(MultipartFile image) {
    if(!image.getContentType().equals("image/jpeg")) {
      throw new ImageUploadException("Only JPG images accepted");
    }
  }
  
  //<start id="method_showSpitterProfile"/> 
  @RequestMapping(value="/{username}", method=RequestMethod.GET)
  public String showSpitterProfile(@PathVariable String username,
          Model model) {
    model.addAttribute(spitterService.getSpitter(username));
    return "spitters/view";
  }
  //<end id="method_showSpitterProfile"/> 

  @RequestMapping(value="/{username}", method=RequestMethod.GET,
          params="edit")
  public String editSpitterProfile(@PathVariable String username,
          Model model) {
    model.addAttribute(spitterService.getSpitter(username));
    return "spitters/edit";
  }
  
  @RequestMapping(value="/{username}", method=RequestMethod.PUT)
  public String updateSpitterFromForm(@PathVariable String username,
          Spitter spitter) {
    spitterService.saveSpitter(spitter);
    return "redirect:/spitters/" + username;
  }

  @RequestMapping(value="/{username}", method=RequestMethod.DELETE)
  public String deleteSpitter(@PathVariable String username) {
    // TODO: Add method to remove spitter
    //       Probably need to logout here, too...if the deleted user is the authenticated user
    return "redirect:/home";
  }
  
  @RequestMapping(value="/{username}/spittles", 
                  method=RequestMethod.GET)
  public String listSpittlesForSpitter(
                      @PathVariable String username, Model model) {
    model.addAttribute(spitterService.getSpitter(username));
    List<Spittle> spittlesForSpitter = spitterService.getSpittlesForSpitter(username);
    model.addAttribute(spittlesForSpitter);
    return "spittles/list";
  }
  
  // Machine-friendly RESTful handler methods follow
  @RequestMapping(method = RequestMethod.GET, 
          headers = "Accept=application/json")
  public @ResponseBody List<Spitter> allSpitters() {
    return spitterService.getAllSpitters();
  }

  @RequestMapping(method = RequestMethod.POST, 
                  headers = "Content-Type=application/json")
  @ResponseStatus(HttpStatus.CREATED)
  public @ResponseBody 
  Spitter createSpitter(@RequestBody Spitter spitter) {
    spitterService.saveSpitter(spitter);
    return spitter;
  }

  //<start id="method_getSpitter_ResponseBody"/> 
  @RequestMapping(value = "/{username}", method = RequestMethod.GET, 
                  headers = {"Accept=text/xml, application/json"})
  public @ResponseBody 
  Spitter getSpitter(@PathVariable String username) {
    return spitterService.getSpitter(username);
  }
  //<end id="method_getSpitter_ResponseBody"/> 

//  @RequestMapping(value = "/{username}", method = RequestMethod.GET, 
//          headers = "Accept=application/json")
//  public @ResponseBody 
//    Spitter getSpitterAsXML(@PathVariable String username) {
//    return spitterService.getSpitter(username);
//  }
  
  //<start id="method_putSpitter"/> 
  @RequestMapping(value = "/{username}", method = RequestMethod.PUT, 
                  headers = "Content-Type=application/json")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void updateSpitter(@PathVariable String username, 
                        @RequestBody Spitter spitter) {
    spitterService.saveSpitter(spitter);
  }
  //<end id="method_putSpitter"/> 

  @RequestMapping(value = "/{username}/spittles", 
                  method = RequestMethod.GET, 
                  headers = "Accept=application/json")
  public @ResponseBody  
  List<Spittle> getSpittlesForSpitter(@PathVariable String username) {
    return spitterService.getSpittlesForSpitter(username);
  }
}
