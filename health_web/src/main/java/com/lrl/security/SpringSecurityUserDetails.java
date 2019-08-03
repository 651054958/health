package com.lrl.security;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lrl.pojo.Permission;
import com.lrl.pojo.Role;
import com.lrl.pojo.User;
import com.lrl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author LRL
 * @version 1.0
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 * @date 2019/7/28 10:28
 */
@Component("springSecurityUserService")
public class SpringSecurityUserDetails implements UserDetailsService {
    @Autowired
    private BCryptPasswordEncoder encoder;

    @Reference
    private UserService userService;
    /**
     * Locates the user based on the username. In the actual implementation, the search
     * may possibly be case sensitive, or case insensitive depending on how the
     * implementation instance is configured. In this case, the <code>UserDetails</code>
     * object that comes back may have a username that is of a different case than what
     * was actually requested..
     *
     * @param username the username identifying the user whose data is required.
     * @return a fully populated user record (never <code>null</code>)
     * @throws UsernameNotFoundException if the user could not be found or the user has no
     *                                   GrantedAuthority
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findUserByUsername(username);
        if (null==user) {
            return null;
        }
        List<SimpleGrantedAuthority> list = new ArrayList<>();
        Set<Role> roles = user.getRoles();

        if (null!=roles) {
            roles.forEach(role->{
                list.add(new SimpleGrantedAuthority(role.getKeyword()));
                Set<Permission> permissions = role.getPermissions();
                if (null!=permissions) {
                    permissions.forEach(permission -> list.add(new SimpleGrantedAuthority(permission.getKeyword())));
                }
            });
        }
        org.springframework.security.core.userdetails.User userDetails = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), list);
        return userDetails;
    }
}