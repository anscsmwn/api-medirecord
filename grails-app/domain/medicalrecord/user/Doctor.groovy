package medicalrecord.user

import io.jsonwebtoken.Claims
import org.springframework.data.mongodb.core.mapping.Document
import org.mindrot.jbcrypt.BCrypt
import io.jsonwebtoken.Jwts

@Document("doctors")
class Doctor {
    String name
    String email
    String password
    List <String> specializations
    static mapWith = "mongo"

    static constraints = {
        name nullable: false
        email nullable: false, unique: true, maxSize: 255
        password nullable: false
        specializations nullable: true, validator: { val, obj ->
            return val?.every { it.length() <= 50 }
        }
    }

    def beforeInsert() {
        password = BCrypt.hashpw(password, BCrypt.gensalt())
    }

    static Long getIdFromToken(String secretKey, String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey.getBytes('UTF-8'))
                .parseClaimsJws(token)
                .getBody()
        return Long.parseLong(claims.getSubject())
    }

}
