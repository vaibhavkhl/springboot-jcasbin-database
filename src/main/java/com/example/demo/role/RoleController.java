package com.example.demo.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/roles")
    public Page<Role> getRoles(Pageable pagaeble) {
        return roleService.getRoles(pagaeble);
    }

    @PostMapping("/roles")
    public Role createRole(@Valid @RequestBody Role role) {
        return roleService.createRole(role);
    }

    @PutMapping("/roles/{roleId}")
    public Role updateRole(@PathVariable Long roleId,
                                   @Valid @RequestBody Role roleRequest) {
        return roleService.updateRole(roleId, roleRequest);
    }


    @DeleteMapping("/roles/{roleId}")
    public void deleteRole(@PathVariable Long roleId) {
        roleService.deleteRole(roleId);
    }


}