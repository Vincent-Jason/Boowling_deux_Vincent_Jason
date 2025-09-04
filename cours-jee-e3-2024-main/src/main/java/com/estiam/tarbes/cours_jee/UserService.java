package com.estiam.tarbes.cours_jee;

import java.beans.Transient;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private EventRepository eventRepository;
    
    @Transactional
    public void createUser(User user) {

        User bob = userRepository.findByName("Bob");
        if(bob == null) {
            bob = new User("Bob", "bob@estiam.fr", "bobby");
            userRepository.save(bob);
        }
        
        User ana = userRepository.findByName("Ana");
        if(ana == null) {
            ana = new User("Ana", "ana@estiam.fr", "anana");
            userRepository.save(ana);
        }
        
        Event birthday = new Event();
        birthday.setName("Birthday of " + user.getName());
        birthday.setDate(LocalDate.of(1990, 5, 5));
        birthday.setDescription("birthday of " + user.getName());
        eventRepository.save(birthday);
        user.setBirthday(birthday);

        Event dentist = new Event();
        dentist.setName("Dentist");
        dentist.setDate(LocalDate.now().plusDays(3));
        dentist.setDescription("Event for " + user.getName());
        eventRepository.save(dentist);
        user.getAppointments().add(dentist);

        Event doctor = new Event();
        doctor.setName("Doctor");
        doctor.setDate(LocalDate.now().plusDays(1));
        doctor.setDescription("Event for " + user.getName());
        eventRepository.save(doctor);
        user.getAppointments().add(doctor);
        userRepository.save(user);

        Event foot = new Event();
        foot.setName("Foot");
        foot.setDate(LocalDate.of(2025, 11, 5));
        foot.setDescription("un foot entre copains");
        
        foot.getParticipants().add(user);
        user.getGames().add(foot);
        foot.getParticipants().add(bob);
        bob.getGames().add(foot);
        eventRepository.save(foot);
        userRepository.save(user);
        userRepository.save(bob);
        
        Event tennis = new Event();
        tennis.setName("Tennis");
        tennis.setDate(LocalDate.of(2025, 11, 6));
        tennis.setDescription("un tennis de ouf");
        
        tennis.getParticipants().add(user);
        user.getGames().add(tennis);
        tennis.getParticipants().add(ana);
        ana.getGames().add(tennis);
        
        eventRepository.save(tennis);
        userRepository.save(user);
        userRepository.save(ana);
    }

    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
