import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class CreateDummies {

	public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
		// TODO Auto-generated method stub
		Admin admin = new Admin("Admin1","12345");          
		
		admin.addStudent("CTAN203","978775","Ching Fhen","Male","U1920787D","Singaporean");
		admin.addStudent("ACAN203","978775","Caitlyn","Female","U1920787D","Singaporean");
		
		admin.addCourseIndex("Data Science and AI", "SCSE",11035,5);
		admin.addCourseIndex("Computer Science", "SCSE",11033,5);
	
		
		
		
	}

}
