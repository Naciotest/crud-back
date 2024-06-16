package com.rosada.crud.model;

import com.rosada.crud.enums.State;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

@Data
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Entity
public class UserCrud {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max =20, min = 1)
    private String name;

    @NotBlank
    @Email
    private String email;

    @Size(max = 20, min = 3)
    private String country;

    @NotNull
    @Past
    private LocalDate birthDate;

    @NotNull
    private State state;

    // MappedBy: Indica el nombre del campo en la clase destino
    // CascadeType.ALL: Indica que todas las operaciones de persistencia realizadas en User se propagarán a UserProfile.
    // Por ejemplo, si se elimina un User, también se eliminará su UserProfile.

    // OrphanRemoval: Cuando está establecido en true, indica que si un UserProfile se desasocia de un User,
    // el UserProfile será eliminado de la base de datos.

    @OneToOne(mappedBy = "userCrud", cascade = CascadeType.ALL, orphanRemoval = true)
    //relación uno a uno entre User y UserProfile, donde cada usuario tiene un perfil único.
    private UserProfile profile;

    @OneToMany(mappedBy = "userCrud", cascade = CascadeType.ALL, orphanRemoval = true)
    //si se elimina un User, todos los Post asociados también se eliminarán.
    //relación uno a muchos entre User y Post, donde un usuario puede tener varios posts.
    private List<Post> posts = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))

    // @JoinTable: Especifica la tabla intermedia (user_role)
    // que se utiliza para almacenar la relación muchos a muchos entre User y Role
    // joinColumns e inverseJoinColumns: Especifican las columnas en la tabla intermedia que hacen referencia a
    // las claves primarias de User (user_id) y Role (role_id), respectivamente.

    //relación muchos a muchos entre User y Role, donde un usuario puede tener varios roles
    // y un rol puede estar asociado a varios usuarios.

    //LAZY: Indica que la recuperación de datos de la entidad relacionada (Role) será perezosa.
    // Esto significa que los datos de Role no se cargarán automáticamente cuando se recupere un User
    // a menos que se acceda explícitamente a ellos.

    //EAGER: Indica que la recuperación de datos de la entidad relacionada (Role) será ansiosa.
    // En este caso, los datos de Role se cargarán automáticamente junto con el User cuando se recupere un User.
    private Set<Role> roles = new HashSet<>();


}







