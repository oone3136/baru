package com.test944.services;

import com.test944.exception.ResourceNotFoundException;
import com.test944.model.Users;
import com.test944.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
@Slf4j
public class UsersServis {

    private final UsersRepository repository;

    private final PasswordEncoder passwordEncoder;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    public List<Users> getAllUsers(){
        return repository.findAll();
    }
    public Users getDataUser(String id){

        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Bidang dengan id = " + id));
    }
    public Users createUsers(Users request){
        try {
            Users users = new Users();
            users.setId(request.getId());
            users.setNamaLengkap(request.getNamaLengkap());
            users.setEmail(request.getEmail());
            users.setNomorTelepon(request.getNomorTelepon());
            users.setIsAktif(true);
            users.setRoles("user");
            users.setPassword(passwordEncoder.encode(request.getPassword()));
            Users create =new Users(request.getId(), request.getNamaLengkap(), request.getEmail(), request.getNomorTelepon(),request.getPassword(), request.getRoles(), request.getIsAktif());
            return repository.save(create);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    public Users updateKaryawan(Users req, String id){
         repository.findById(id);
         Users users = new Users();
        users.setId(req.getId());
        users.setNamaLengkap(req.getNamaLengkap());
        users.setEmail(req.getEmail());
        users.setNomorTelepon(req.getNomorTelepon());
        users.setPassword(req.getPassword());
        users.setRoles(req.getRoles());
        Users update =new Users(req.getId(), req.getNamaLengkap(), req.getEmail(), req.getNomorTelepon(), req.getPassword(), req.getRoles());

        return repository.save(update);
    }
    public void delete(String ID_Karyawan){
        repository.deleteById(ID_Karyawan);
    }
}
