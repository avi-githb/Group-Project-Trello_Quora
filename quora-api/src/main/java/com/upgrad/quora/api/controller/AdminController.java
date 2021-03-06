package com.upgrad.quora.api.controller;

import com.upgrad.quora.api.model.UserDeleteResponse;
import com.upgrad.quora.service.business.UserDeleteService;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * This endpoint is used to delete a user from the Quora Application. Only an admin is authorized to access this endpoint.
 */


@RestController
@RequestMapping(method = RequestMethod.DELETE, path = "/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AdminController {

    @Autowired
    private UserDeleteService userDeleteService;

    @RequestMapping("/admin/user/{userId}")
    public ResponseEntity<UserDeleteResponse> userDelete(@RequestHeader("authorization") final String authorization, @PathVariable("userId") final String userId) throws AuthorizationFailedException, UserNotFoundException {

        String iDOfDeletedUser = userDeleteService.deleteUser(authorization, userId);

        UserDeleteResponse userDeleteResponse = new UserDeleteResponse().id(iDOfDeletedUser).status("USER SUCCESSFULLY DELETED");
        return new ResponseEntity<UserDeleteResponse>(userDeleteResponse, HttpStatus.OK);
    }

}