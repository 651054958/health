import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author LRL
 * @version 1.0
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 * @date 2019/7/29 20:24
 */
public class Test {
    public static void main(String[] args) {
        /*BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String admin = encoder.encode("admin");
        System.out.println(admin);
        System.out.println(encoder.matches("visitor","$2a$10$/MCGUn7bsKkWXI.8IksOUesq5pQlHDCHJQ1hjsKhd1QMN2.J/1kXC"));*/
        String dat2 = "2019-07-aa";
        String rep = dat2.replaceAll(".", "-");
        System.out.println(rep);
    }
}