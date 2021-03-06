package com.upgrad.quora.service.business;

import com.upgrad.quora.service.dao.UserDao;
import com.upgrad.quora.service.entity.UserAuthTokenEntity;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * This class is used to get the details of any user in the Quora Application.
 */
@Service
public class UserBusinessService {

    @Autowired
    private UserDao userDao;

    @Transactional(propagation = Propagation.REQUIRED)

    /**
     *this method return all the details of the user as UserEntity.
     */
    public UserEntity getUserByUuId(final String userUuid, String authToken) throws UserNotFoundException, AuthorizationFailedException {

        UserAuthTokenEntity userAuthTokenEntity = userDao.getUserByAuthtoken(authToken);

        /* Now we have to check 3 conditions, as below*/

        /* Case 1:- User and Auth Token both not exist in database, return "User has not signed in" with code 'ATHR-001' */

        if (userAuthTokenEntity == null) {
            throw new AuthorizationFailedException("ATHR-001", "User has not signed in");

        }
        /* Case 2:- User exists but Auth token does not exist in database, return "User is signed out.Sign in first to get user details" with code 'ATHR-002' */

        if (userAuthTokenEntity.getLogoutAt() != null) {
            throw new AuthorizationFailedException("ATHR-002", "User is signed out.Sign in first to get user details");
        }

        /* Case 3:- User does not exist in database, return "User with entered uuid does not exist" with code 'USR-001' */
        UserEntity userEntity = userDao.getUserFromUuid(userUuid);

        if (userEntity == null) {

            throw new UserNotFoundException("USR-001", "User with entered uuid does not exist");
        }
        return userEntity;
    }

}
