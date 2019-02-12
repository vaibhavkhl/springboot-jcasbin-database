package com.example.demo.role;

import com.example.demo.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Page<Role> getRoles(Pageable pagaeble) {
        return roleRepository.findAll(pagaeble);
    }

    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    public Role updateRole(Long roleId,
                           Role roleRequest) {
        return roleRepository.findById(roleId)
                .map(role -> {
                    role.setName(roleRequest.getName());
                    return roleRepository.save(role);
                }).orElseThrow(() -> new ResourceNotFoundException("Role not found with id " + roleId));
    }

    public ResponseEntity<?> deleteRole(Long roleId) {
        return roleRepository.findById(roleId)
                .map(role -> {
                    roleRepository.delete(role);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Role not found with id " + roleId));
    }


}
