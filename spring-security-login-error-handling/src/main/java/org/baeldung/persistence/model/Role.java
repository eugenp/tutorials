package org.baeldung.persistence.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity(name="role")
@Table(name = "role")
public class Role {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

       
        @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER,  cascade = CascadeType.ALL)
        @JoinColumn(name = "user_id")
        private User user;
        
        @Column(name="role")
        private Integer role;

        public Role(){
            super();
            
         }
        public Role(Integer role){
           super();
           this.role = role; 
        }
        public Role(Integer role, User user){
            super();
            this.role = role; 
            this.user = user;
         }
        public Long getId() {
                return id;
        }
        public void setId(Long id) {
                this.id = id;
        }
        public User getUser() {
                return user;
        }
        public void setUser(User user) {
                this.user = user;
        }
        public Integer getRole() {
                return role;
        }
        public void setRole(Integer role) {
                this.role = role;
        }
}