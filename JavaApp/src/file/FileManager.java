package file;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

// 직렬화가 가능한 객체들을 직렬화하여 .txt 파일로 지정된 경로의 DB 폴더에 저장하고 저장된 .txt 파일들을 역직렬화를 통해 다시 Object 객체로 변환
public class FileManager {	
	/* 
	 * email을 파일명에 맞게 변환, @를 0으로 .을 1로 바꾸어 저장 ( 예시 qwe@naver.com -> qwe0naver1com )
	 * User 객체를 파일로 저장할 때 사용
	 * return : 변환된 파일명
	 */
	public static String emailToFilename(String email) {
		return email.replace('@','0').replace('.', '1');
	}
	/* 
	 * 현재 프로젝트 소스 폴더의 절대 경로를 가져오는 메서드 ~~경로//프로젝트//src까지 가져옴
	 * 각 DB에서 파일을 가져올 때 파일경로를 설정하기 위해 사용
	 * return : 소스파일에 대한 절대경로
	 */
	public static String getPackageRootDir() {
		System.out.println();
		return System.getProperty("user.dir") +"\\src";
	}

	/*
	 * Object 객체를 파일경로에 저장 or 업데이트 
	 *  기존에 동일한 파일이 없으면 파일 생성, 기존에 동일한 파일이 있으면 덮어쓰기(업데이트)
	 * param : 1. 저장할 Object 객체
	 * 		   2. 저장할 경로 (문자열) ( 소스파일 하위경로로 어디 패키지 어디 폴더에 어떤 파일이름으로 저장할지 명시 )
	 * 			 예시 :  "//패키지//DB폴더//파일명.확장자", "//user//UserDB//user1.txt"
	 * return : 파일이 정상적으로 저장되었는지 여부 리턴
	 */
	public static boolean createUpdateObjectFile(Object obj, String filepath) {	
		filepath = getPackageRootDir() + filepath;
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filepath))) {
			oos.writeObject(obj);
		} catch (IOException e) {
			e.printStackTrace();           
			return false;
		}
		return true;
	}
	
	/*
	 * 파일경로에 해당하는 객체파일을 Object로 변환하여 반환하는 메서드
	 * param : Object로 변환할 파일이 저장된 경로 (문자열) ( 예시 :  "//패키지//DB폴더//파일명.확장자" ) 			
	 * return : Object로 변환한 파일
	 */		
	public static Object readObjectFile(String filepath) {
		filepath = getPackageRootDir() + filepath;
		Object obj = new Object();
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filepath))) {
			obj = ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return obj;
	}

	
	/*
	 * 객체파일(File)을 Object로 변환하여 반환하는 메서드
	 * param : Object로 변환할 객체파일(File)
	 * return : Object로 변환한 파일
	 */
	public static Object readObjectFile(File file) {
		Object obj = new Object();
		// File 객체 -> InputStream -> Object로 변환
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
			obj = ois.readObject(); // Object로 변환
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return obj;
	}

	/*
	 * 폴더에 있는 파일을 Object로 변환하여 ArrayList에 담아 반환하는 메서드
	 * param : Object로 변환할 파일들이 저장된 폴더 경로(문자열)
	 * return : Object로 변환한 파일들이 저장된 리스트 
	 */
	public static ArrayList<Object> readAllObjectFileInDirectory(String dirpath) {
		dirpath = getPackageRootDir() + dirpath;
		ArrayList<Object> objList = new ArrayList<>();		
		File dir = new File(dirpath);
		File[] objFiles = dir.listFiles(); // 폴더의 모든 파일을 배열로 반환
		try {
			for( File file : objFiles) {
				objList.add(readObjectFile(file)); // 각 파일을 Object로 변환
			}
		} catch (Exception e) {
			e.printStackTrace();			
			System.out.println("경로 : "+ dirpath + ", 해당 파일을 Obeject로 변환할 수 없습니다.");
		}
		
		return objList;
	}	

	// 특정 경로에 있는 파일 내용을 한 줄씩 읽어 List<String>로 반환 ( 텍스트 파일 읽는데 사용 )
	public static List<String> readLinesFromFile(String filepath){
		filepath = getPackageRootDir() + filepath;
		List<String> lineList = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
			String line;
			while ((line = br.readLine()) != null) {
				lineList.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lineList;
	}

}
