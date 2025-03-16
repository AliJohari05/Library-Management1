import java.util.*;
import java.lang.StringBuilder;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.Duration;
import java.lang.Long;
public class Main {
    public static long penalty;
    public static int calculateDateTimeDifference(String date1, String time1, String date2, String time2) {
        if (date1 != null && time1 != null && date2 != null && time2 != null && time1.matches("\\d{2}:\\d{2}") && time2.matches("\\d{2}:\\d{2}") && date1.matches("\\d{4}-\\d{2}-\\d{2}") && date2.matches("\\d{4}-\\d{2}-\\d{2}")) {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalDate localDate1 = LocalDate.parse(date1, dateFormatter);
            LocalTime localTime1 = LocalTime.parse(time1, timeFormatter);
            LocalDateTime dateTime1 = LocalDateTime.of(localDate1, localTime1);

            LocalDate localDate2 = LocalDate.parse(date2, dateFormatter);
            LocalTime localTime2 = LocalTime.parse(time2, timeFormatter);
            LocalDateTime dateTime2 = LocalDateTime.of(localDate2, localTime2);

            Duration duration = Duration.between(dateTime2, dateTime1);
            double minutes = duration.toMinutes();
            double hours = minutes / 60.0;
            return  (int)hours;
        }
        return 0;
    }
    public static boolean checkTimeOverlap(String date1, String startTime1, String endTime1, String date2, String startTime2, String endTime2) {
        if (date1 != null && startTime1 != null && endTime1 != null && date2 != null && startTime2 != null && endTime2 != null &&
                startTime1.matches("\\d{2}:\\d{2}") && endTime1.matches("\\d{2}:\\d{2}") &&
                startTime2.matches("\\d{2}:\\d{2}") && endTime2.matches("\\d{2}:\\d{2}") &&
                date1.matches("\\d{4}-\\d{2}-\\d{2}") && date2.matches("\\d{4}-\\d{2}-\\d{2}")) {

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

            LocalDate localDate1 = LocalDate.parse(date1, dateFormatter);
            LocalTime startLocalTime1 = LocalTime.parse(startTime1, timeFormatter);
            LocalTime endLocalTime1 = LocalTime.parse(endTime1, timeFormatter);
            LocalDateTime startDateTime1 = LocalDateTime.of(localDate1, startLocalTime1);
            LocalDateTime endDateTime1 = LocalDateTime.of(localDate1, endLocalTime1);

            LocalDate localDate2 = LocalDate.parse(date2, dateFormatter);
            LocalTime startLocalTime2 = LocalTime.parse(startTime2, timeFormatter);
            LocalTime endLocalTime2 = LocalTime.parse(endTime2, timeFormatter);
            LocalDateTime startDateTime2 = LocalDateTime.of(localDate2, startLocalTime2);
            LocalDateTime endDateTime2 = LocalDateTime.of(localDate2, endLocalTime2);

            return !(endDateTime1.isBefore(startDateTime2) || endDateTime2.isBefore(startDateTime1));
        }
        return false;
    }

    public static HashMap<String ,Library> library = new HashMap<String , Library>();
    public static ArrayList< Book> book = new ArrayList<Book>();
    public static ArrayList< Thesis> thesis = new ArrayList <Thesis>();
    public static HashMap<String , Student> student = new HashMap<String , Student>();
    public static HashMap<String , Staff> staff = new HashMap<String , Staff>();
    public static HashMap<String , Category> category = new HashMap<String , Category>();
    public static ArrayList<Borrow> borrow = new ArrayList<Borrow>();
    public static HashMap<String , ReserveSeat> reserve = new HashMap<String , ReserveSeat>();
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        category.put("null", new Category());
        category.get("null").AddCategory("null|null");
        boolean finish = false;
        while (!finish) {

            StringBuilder order = new StringBuilder();
            order.delete(0, order.length());
            order.append(input.nextLine());
            if (order.toString().equals("finish")) {
                finish = true;
            }
            else if(order.toString().startsWith("add-library#")) {
                order.delete(0,order.indexOf("#")+1);
                String[] arr = order.toString().split("[|]");
                int flag = 0 ;
                for(String str : library.keySet()) {
                    if(str.equals(arr[0])) {
                        flag = 1;
                        break;
                    }
                }
                if(flag == 0) {
                    library.put(arr[0], new Library());
                    String result = library.get(arr[0]).AddLibrary(order.toString());

                    if(result.equals("success")) {
                        System.out.println("success");
                    }
                    else {
                        System.out.println("duplicate-id");
                    }
                }
                else{
                    System.out.println("duplicate-id");
                }


            }
            else if(order.toString().startsWith("add-category#")) {
                order.delete(0,order.indexOf("#")+1);
                String[] arr = order.toString().split("[|]");
                int flag = 0 ;
                for(String str : category.keySet()) {
                    if(str.equals(arr[0])) {
                        flag = 1;
                    }
                }
                if(flag == 0) {
                    category.put(arr[0], new Category());
                }
                String result = category.get(arr[0]).AddCategory(order.toString());
                if(result.equals("success")) {
                    System.out.println("success");
                }
                else {
                    System.out.println("duplicate-id");
                }
            }
            else if(order.toString().startsWith("add-book#")) {
                order.delete(0,order.indexOf("#")+1);
                String[] arr = order.toString().split("[|]");
                int flag = 0 ;
                for(Book it : book) {
                    if(it != null && it.idBook != null && it.idBook.equals(arr[0]) && it.library.equals(arr[7]) && it.category.equals(arr[6])) {
                        flag = 1;
                    }
                }
                if(flag == 0) {
                    book.add(new Book());
                    String result = book.get(book.size()-1).AddBook(order.toString());
                    if(result.equals("success")) {
                        System.out.println("success");
                    }
                    else if(result.equals("duplicate-id")) {
                        System.out.println("duplicate-id");
                    }
                    else if(result.equals("both")) {
                        System.out.println("not-found");

                    }
                    else {
                        System.out.println("not-found");
                    }
                }else{
                    System.out.println("duplicate-id");
                }


            }
            else if(order.toString().startsWith("edit-book#")) {
                order.delete(0,order.indexOf("#")+1);
                String[] arr = order.toString().split("[|]");
                int flag = 0 ;
                Book temp = null;
                for(Book it : book) {
                    if(it != null && it.idBook != null && it.idBook.equals(arr[0]) && it.library.equals(arr[1])) {
                        flag = 1;
                        temp = it;
                    }
                }
                if(flag == 1) {
                    String result = temp.EditBook(order.toString());
                    if (result.equals("success")) {

                        System.out.println("success");
                    } else {
                        System.out.println("not-found");
                    }
                }
                else {
                    System.out.println("not-found");
                }
            }
            else if(order.toString().startsWith("remove-book#")) {
                order.delete(0,order.indexOf("#")+1);
                String[] arr = order.toString().split("[|]");
                int flag = 0 ;
                Book temp = null;
                for(Book it : book) {
                    if(it != null && it.idBook != null && it.idBook.equals(arr[0]) && it.library.equals(arr[1])) {
                        flag = 1;
                        temp = it;
                    }
                }
                if(flag == 1) {
                    String result = temp.RemoveBook(order.toString());
                    output(result);
                }
                else {
                    System.out.println("not-found");
                }
            }
            else if(order.toString().startsWith("add-thesis#")) {
                order.delete(0,order.indexOf("#")+1);
                String[] arr = order.toString().split("[|]");
                int flag = 0 ;
                for(Thesis it : thesis) {
                    if(it.idThesis != null && it.idThesis.equals(arr[0]) && it.library.equals(arr[6]) && it.category.equals(arr[5])) {
                        flag = 1;
                        break;
                    }
                }
                if(flag == 0) {
                    thesis.add(new Thesis());
                    String result = thesis.get(thesis.size()-1).AddThesis(order.toString());
                    if(result.equals("success")) {
                        System.out.println("success");
                    }
                    else if(result.equals("duplicate-id")) {
                        System.out.println("duplicate-id");
                    }
                    else if (result.equals("both")) {
                        System.out.println("not-found");

                    }
                    else if(result.equals("not-found")) {
                        System.out.println("not-found");
                    }
                }

                else {
                    System.out.println("duplicate-id");
                }
            }
            else if(order.toString().startsWith("edit-thesis#")) {
                order.delete(0,order.indexOf("#")+1);
                String[] arr = order.toString().split("[|]");
                Thesis temp = null;
                for(Thesis it : thesis) {
                    if(it.idThesis != null && it.idThesis.equals(arr[0]) && it.library.equals(arr[1])) {
                        temp = it;
                    }
                }
                if(temp != null) {
                    String result = temp.EditThesis(order.toString());
                    if (result.equals("success")) {

                        System.out.println("success");
                    } else {
                        System.out.println("not-found");
                    }
                }
                else {
                    System.out.println("not-found");
                }
            }
            else if(order.toString().startsWith("remove-thesis#")) {
                order.delete(0,order.indexOf("#")+1);
                String[] arr = order.toString().split("[|]");
                Thesis temp = null;
                for(Thesis it : thesis) {
                    if(it.idThesis != null  && it.idThesis.equals(arr[0]) && it.library.equals(arr[1])) {
                        temp = it;
                    }
                }
                if(temp != null) {
                    String result = temp.RemoveThesis(order.toString());
                    output(result);
                }
                else {
                    System.out.println("not-found");
                }
            }
            else if(order.toString().startsWith("add-student#")) {
                order.delete(0,order.indexOf("#")+1);
                String[] arr = order.toString().split("[|]");
                int flag = 0 ;
                for(String str : student.keySet()) {
                    if(str.equals(arr[0])) {
                        flag = 1;
                    }
                }
                if(flag == 0) {
                    Main.student.put(arr[0],new Student());
                }
                String result = student.get(arr[0]).AddStudent(order.toString());
                if(result.equals("success")) {
                    System.out.println("success");
                }
                else if(result.equals("duplicate-id")) {
                    System.out.println("duplicate-id");
                }
            }
            else if(order.toString().startsWith("edit-student#")) {
                order.delete(0,order.indexOf("#")+1);
                String[] arr = order.toString().split("[|]");
                if(student.get(arr[0]) != null) {
                    String result = student.get(arr[0]).EditStudent(order.toString());
                    if (result.equals("success")) {

                        System.out.println("success");
                    } else {
                        System.out.println("not-found");
                    }
                }else{
                    System.out.println("not-found");
                }
            }
            else if(order.toString().startsWith("remove-student#")) {
                order.delete(0,order.indexOf("#")+1);
                String[] arr = order.toString().split("[|]");
                if(student.get(arr[0]) != null) {
                    String result = student.get(arr[0]).RemoveStudent(order.toString());
                    if (result.equals("success")) {
                        System.out.println("success");
                    }
                    else if(result.equals("not-allowed")){
                        System.out.println("not-allowed");
                    }
                    else {
                        System.out.println("not-found");
                    }
                }
                else {
                    System.out.println("not-found");
                }
            }
            else if(order.toString().startsWith("add-staff#")) {
                order.delete(0,order.indexOf("#")+1);
                String[] arr = order.toString().split("[|]");
                int flag = 0 ;
                for(String str : staff.keySet()) {
                    if(str.equals(arr[0])) {
                        flag = 1;
                    }
                }
                if(flag == 0) {
                    Main.staff.put(arr[0],new Staff());
                }
                String result = staff.get(arr[0]).AddStaff(order.toString());
                if(result.equals("success")) {
                    System.out.println("success");
                }
                else if(result.equals("duplicate-id")) {
                    System.out.println("duplicate-id");
                }
            }
            else if(order.toString().startsWith("edit-staff#")) {
                order.delete(0,order.indexOf("#")+1);
                String[] arr = order.toString().split("[|]");
                if(staff.get(arr[0]) != null) {
                    String result = staff.get(arr[0]).EditStaff(order.toString());
                    if (result.equals("success")) {

                        System.out.println("success");
                    } else {
                        System.out.println("not-found");
                    }
                }else{
                    System.out.println("not-found");
                }
            }
            else if(order.toString().startsWith("remove-staff#")) {
                order.delete(0,order.indexOf("#")+1);
                String[] arr = order.toString().split("[|]");
                if(staff.get(arr[0]) != null) {
                    String result = staff.get(arr[0]).RemoveStaff(order.toString());
                    if (result.equals("success")) {
                        System.out.println("success");
                    }
                    else if(result.equals("not-allowed")) {
                        System.out.println("not-allowed");
                    }
                    else {
                        System.out.println("not-found");
                    }
                }
                else {
                    System.out.println("not-found");
                }

            }
            else if(order.toString().startsWith("borrow#")) {
                order.delete(0,order.indexOf("#")+1);
                String[] arr = order.toString().split("[|]");
                int flagStudent = 0 ;
                int flagStudentPass = 0 ;
                for(String s : student.keySet()) {
                    if(s.equals(arr[0])) {
                        flagStudent = 1;
                        if(arr[1].equals(student.get(s).password)){
                            flagStudentPass = 1;
                            break;
                        }
                    }
                }
                int flagStaff = 0 ;
                int flagStaffPss = 0 ;
                for(String s : staff.keySet()) {
                    if(s.equals(arr[0])) {
                        flagStaff = 1;
                        if(arr[1].equals(staff.get(s).password)) {
                            flagStaffPss = 1;
                            break;
                        }
                    }
                }
                int flagBook = 0 ;
                Book temp = null;
                for(Book it : book) {
                    if(it != null && it.idBook != null && it.idBook.equals(arr[3]) && it.library.equals(arr[2])) {
                        flagBook = 1;
                        temp = it;
                        break;
                    }
                }
                Thesis instance = null;
                int flagThesis = 0 ;
                for(Thesis it : thesis) {
                    if(it.idThesis != null && it.idThesis.equals(arr[3]) && it.library.equals(arr[2])) {
                        instance = it;
                        flagThesis = 1;
                        break;
                    }
                }

                if(( flagBook == 0 && flagThesis == 0 && flagStaffPss == 0 && flagStudentPass == 0)){
                    System.out.println("not-found");

                }

                else if(((flagStudent == 0 && flagStaff == 0) ||( flagBook == 0 && flagThesis == 0 )) && ((temp != null && temp.number == 0) || ( instance != null && instance.isBorrow ) || (student.get(arr[0]) != null && student.get(arr[0]).RestrictionsBorrowing >= 3) || (staff.get(arr[0]) != null && staff.get(arr[0]).RestrictionsBorrowing >= 5 ))  ){
                    System.out.println("not-found");
                }
                else if(((flagStudent == 0 && flagStaff == 0) ||( flagBook == 0 && flagThesis == 0 ))) {
                    System.out.println("not-found");
                }
                else if((flagStaff == 1 && flagStaffPss == 0) ||( flagStudent == 1 && flagStudentPass == 0 )) {
                    System.out.println("invalid-pass");
                }
                else if((temp != null && temp.number == 0) || ( instance != null && instance.isBorrow ) || (student.get(arr[0]) != null && student.get(arr[0]).RestrictionsBorrowing >= 3) || (staff.get(arr[0]) != null && staff.get(arr[0]).RestrictionsBorrowing >= 5 )){
                    System.out.println("not-allowed");
                }
                else{
                    for(Borrow it : borrow) {
                        if(it.idUser.equals(arr[0]) && it.idLibrary.equals(arr[2]) && it.idBook.equals(arr[3]) && Main.calculateDateTimeDifference(arr[4],arr[5],it.dateOfBorrow,it.clockOfBorrow) > 0) {
                            String tempDate = it.dateOfBorrow;
                            String tempClock = it.clockOfBorrow;
                            it.dateOfBorrow = arr[4];
                            it.clockOfBorrow = arr[5];
                            arr[4] = tempDate;
                            arr[5] = tempClock;

                        }

                    }

                    borrow.add(new Borrow(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5]));


                    if(flagBook == 1){
                        library.get(arr[2]).idBookOfBorrow.add(arr[3]);
                        temp.number -= 1;
                        if(flagStudent == 1){
                            student.get(arr[0]).RestrictionsBorrowing += 1;
                        }
                        else {
                            staff.get(arr[0]).RestrictionsBorrowing += 1;
                        }
                    }
                    else if(flagThesis == 1){
                        library.get(arr[2]).idThesisOfBorrow.add(arr[3]);
                        instance.isBorrow = true;
                        if(flagStudent == 1){
                            student.get(arr[0]).RestrictionsBorrowing += 1;
                        }
                        else {
                            staff.get(arr[0]).RestrictionsBorrowing += 1;
                        }
                    }
                    System.out.println("success");
                }
            }
            else if(order.toString().startsWith("return#")) {
                order.delete(0,order.indexOf("#")+1);
                String[] arr = order.toString().split("[|]");
                int flagStudent = 0 ;
                int flagStudentPass = 0 ;
                for(String s : student.keySet()) {
                    if(s.equals(arr[0])) {
                        flagStudent = 1;
                        if(arr[1].equals(student.get(s).password)){
                            flagStudentPass = 1;
                            break;
                        }
                    }
                }
                int flagStaff = 0 ;
                int flagStaffPss = 0 ;
                for(String s : staff.keySet()) {
                    if(s.equals(arr[0])) {
                        flagStaff = 1;
                        if(arr[1].equals(staff.get(s).password)) {
                            flagStaffPss = 1;
                            break;
                        }
                    }
                }
                int flagBook = 0 ;
                Book temp = null;
                for(Book it : book) {
                    if(it != null && it.idBook != null && it.idBook.equals(arr[3]) && it.library.equals(arr[2])) {
                        flagBook = 1;
                        temp = it;
                        break;
                    }
                }
                Thesis instance = null;
                int flagThesis = 0 ;
                for(Thesis it : thesis) {
                    if(it != null && it.idThesis != null  && it.idThesis.equals(arr[3]) && it.library.equals(arr[2])) {
                        instance = it;
                        flagThesis = 1;
                        break;
                    }
                }
                int check = 0 ;
                String date = "";
                String time = "";
                Borrow myBorrow = null;
                myBreakLabel:
                for(Borrow it : borrow) {
                    if(it.idUser.equals(arr[0]) && it.idLibrary.equals(arr[2]) && it.idBook.equals(arr[3])) {
                        if(flagBook == 1){
                            for(Book i : book) {
                                if(i.idBook != null && i.library != null && i.idBook.equals(arr[3]) && i.library.equals(arr[2]) && i.number != i.cteNumber && it.fine ==0) {
                                    check = 1;
                                    myBorrow = it;
                                    date = it.dateOfBorrow;
                                    time = it.clockOfBorrow;
                                    break myBreakLabel;
                                }
                            }
                        }
                        if(flagThesis == 1){
                            for(Thesis i : thesis) {
                                if(i.idThesis != null && i.library != null && i.idThesis.equals(arr[3])&& i.library.equals(arr[2]) && i.isBorrow && it.fine == 0) {
                                    check = 1;
                                    myBorrow = it;
                                    date = it.dateOfBorrow;
                                    time = it.clockOfBorrow;
                                    break myBreakLabel;
                                }
                            }
                        }

                    }
                }
                double fine = 0;
                if(check == 1) {
                    if (flagStudent == 1 && flagStudentPass == 1) {
                        if (flagBook == 1) {
                            fine = (Main.calculateDateTimeDifference(arr[4], arr[5], date, time) - 10 * 24) * 50;

                        } else if (flagThesis == 1) {
                            fine = (Main.calculateDateTimeDifference(arr[4], arr[5], date, time) - 7 * 24) * 50;
                        }
                    }
                    if (flagStaff == 1 && flagStaffPss == 1) {
                        if (flagBook == 1) {
                            fine = (Main.calculateDateTimeDifference(arr[4], arr[5], date, time) - 14 * 24) * 100;

                        } else if (flagThesis == 1) {
                            fine = (Main.calculateDateTimeDifference(arr[4], arr[5], date, time) - 10 * 24) * 100;
                        }
                    }
                }

                if(((( flagBook == 0 && flagThesis == 0 ) )) && ((flagStudent == 1 || flagStaff == 1) && ( flagStaffPss == 0 &&  flagStudentPass == 0 ))) {
                    System.out.println("not-found");
                }
                else if((flagStudent == 0 && flagStaff == 0) ||( flagBook == 0 && flagThesis == 0 ) || check == 0){
                    System.out.println("not-found");
                }
                else if((flagStaff == 1 && flagStaffPss == 0) ||( flagStudent == 1 && flagStudentPass == 0 )) {
                    System.out.println("invalid-pass");
                }
                else if((flagBook == 1 || flagThesis == 1) && ( (flagStudent == 1 && flagStudentPass == 1 )||( flagStaff == 1 && flagStaffPss == 1 )) && check == 1) {


                    if(flagBook == 1){
                        library.get(arr[2]).idBookOfBorrow.remove(arr[3]);
                        temp.number += 1;
                    }
                    if(flagThesis == 1){
                        library.get(arr[2]).idThesisOfBorrow.remove(arr[3]);
                        instance.isBorrow = false;
                    }
                    if(flagStaff == 1){
                        staff.get(arr[0]).RestrictionsBorrowing -= 1 ;
                    }
                    if(flagStudent == 1){
                        student.get(arr[0]).RestrictionsBorrowing -= 1 ;
                    }



                    if(fine > 0){
                        Main.penalty += (long) fine;
                        myBorrow.fine = (long)fine;
                        System.out.println((long)fine);
                    }
                    else {
                        borrow.remove(myBorrow);
                        System.out.println("success");
                    }
                }
            }
            else if (order.toString().startsWith("search#")) {
                order.delete(0,order.indexOf("#")+1);
                String[] arr = order.toString().split("[|]");
                TreeSet<String> resultOfSearch = new TreeSet<>();
                for(Book it : book) {
                    if(it != null &&  ((  it.title != null && it.title.toLowerCase().contains(arr[0].toLowerCase()) ) || ( it.author != null && it.author.toLowerCase().contains(arr[0].toLowerCase())) || (it.publisher != null && it.publisher.toLowerCase().contains(arr[0].toLowerCase()) ))) {
                        resultOfSearch.add(it.idBook);
                    }
                }
                for(Thesis it : thesis) {
                    if(it != null && ((it.title != null && it.title.toLowerCase().contains(arr[0].toLowerCase()))  || (it.studentName != null && it.studentName.toLowerCase().contains(arr[0].toLowerCase())) || ( it.professorName != null &&  it.professorName.toLowerCase().contains(arr[0].toLowerCase())))) {
                        resultOfSearch.add(it.idThesis);
                    }
                }

                if(!resultOfSearch.isEmpty()) {
                    outputForSearch(resultOfSearch);

                }
                else{
                    System.out.println("not-found");
                }
            }
            else if(order.toString().startsWith("search-user#")) {
                int flagPass = 0 ;
                int flagId = 0 ;
                order.delete(0,order.indexOf("#")+1);
                String[] arr = order.toString().split("[|]");
                TreeSet<String> resultOfSearch = new TreeSet<>();
                if(student.get(arr[0]) != null){
                    for(String stu : student.keySet())
                        if(stu.equals(arr[0])) {
                            flagId = 1;
                            break;
                        }
                }
                if(staff.get(arr[0]) != null){
                    for(String stu : staff.keySet()){
                        if(stu.equals(arr[0])) {
                            flagId = 1;
                            break;
                        }
                    }
                }

                if(student.get(arr[0]) != null && student.get(arr[0]).password.equals(arr[1])) {
                    flagPass = 1;
                }
                if(staff.get(arr[0]) != null && staff.get(arr[0]).password.equals(arr[1])) {
                    flagPass = 1;
                }
                for(Student it : student.values()) {
                    if( it != null &&  ((it.firstName != null && it.firstName.toLowerCase().contains(arr[2].toLowerCase())) || ( it.lastName != null && it.lastName.toLowerCase().contains(arr[2].toLowerCase())))) {
                        resultOfSearch.add(it.studentNumber);

                    }
                }
                for(Staff it : staff.values()) {
                    if(it != null &&  ((it.firstName != null && it.firstName.toLowerCase().contains(arr[2].toLowerCase())) || (it.lastName != null && it.lastName.toLowerCase().contains(arr[2].toLowerCase())))) {
                        resultOfSearch.add(it.id);
                    }
                }

                if(!resultOfSearch.isEmpty() && flagId == 1 && flagPass == 1 ) {
                    outputForSearch(resultOfSearch);

                }else if(flagPass == 0 && flagId == 1) {
                    System.out.println("invalid-pass");
                }
                else{
                    System.out.println("not-found");
                }
            }
            else if(order.toString().startsWith("reserve-seat#")) {
                order.delete(0,order.indexOf("#")+1);
                String[] arr = order.toString().split("[|]");
                int flagId = 0 ;
                for(String it : student.keySet()) {
                    if(it.equals(arr[0])) {
                        flagId = 1;
                        break;
                    }
                }
                for(String it : staff.keySet()) {
                    if(it.equals(arr[0])) {
                        flagId = 2;
                        break;
                    }
                }
                int flagLibrary = 0;
                for(String it : library.keySet()) {
                    if(it.equals(arr[2])) {
                        flagLibrary = 1;
                        break;
                    }
                }
                int flagReserve = 0;
                if(library.get(arr[2]) != null) {
                    for(ReserveSeat it : library.get(arr[2]).reserveSeats){
                        if( checkTimeOverlap(it.dete,it.reserveTime,it.deliveryTime,arr[3],arr[4],arr[5]) && it.number == 0) {
                            flagReserve = 1;
                            break;
                        }
                    }
                }

                double timeDifference = calculateDateTimeDifference(arr[3],arr[5],arr[3],arr[4]) ;
                if(flagId == 0 || flagLibrary == 0) {
                    System.out.println("not-found");
                }
                else if(flagId == 1 && student.get(arr[0]) != null && !student.get(arr[0]).password.equals(arr[1])) {

                    System.out.println("invalid-pass");

                }
                else if(flagId == 2 && staff.get(arr[0]) != null && !staff.get(arr[0]).password.equals(arr[1])) {

                    System.out.println("invalid-pass");

                }
                else if(timeDifference > 8 || (student.get(arr[0]) != null && student.get(arr[0]).reserveSeat.get(arr[3]) != null  && student.get(arr[0]).reserveSeat.get(arr[3])) || (staff.get(arr[0]) != null && staff.get(arr[0]).reserveSeat.get(arr[3]) != null  && staff.get(arr[0]).reserveSeat.get(arr[3]))) {
                    System.out.println("not-allowed");
                }
                else if(flagReserve == 1  ) {
                    System.out.println("not-available");
                }
                else{
                    if(flagId == 1 && student.get(arr[0]) != null) {
                        student.get(arr[0]).reserveSeat.put(arr[3],true);
                    }
                    else if(flagId == 2 && staff.get(arr[0]) != null) {
                        staff.get(arr[0]).reserveSeat.put(arr[3],true);
                    }
                    library.get(arr[2]).reserveSeats.add(new ReserveSeat(arr[0],arr[1],arr[2],arr[3],arr[4],arr[5]));
                    System.out.println("success");
                }


            }
            else if(order.toString().startsWith("category-report#")) {
                order.delete(0,order.indexOf("#")+1);
                String[] arr = order.toString().split("[|]");
                int flagId = 0 ;
                for(String it : category.keySet()) {
                    if(it.equals(arr[0])) {
                        flagId = 1;
                    }
                }
                int countBook = 0;
                int countThesis = 0;
                for(Book it : book) {
                        if(it.category != null && it.category.equals(arr[0])) {
                            countBook += it.cteNumber;
                        }

                }
                for(Thesis it : thesis) {

                        if(it.category != null && it.category.equals(arr[0])) {
                            countThesis+=1;
                        }

                }
                if(flagId == 0) {
                    System.out.println("not-found");
                }
                else{
                    System.out.println(countBook+" " + countThesis);
                }
            }
            else if(order.toString().startsWith("library-report#")) {
                order.delete(0,order.indexOf("#")+1);
                String[] arr = order.toString().split("[|]");
                int flagId = 0 ;
                for(String it : library.keySet()) {
                    if(it.equals(arr[0])) {
                        flagId = 1;
                        break;
                    }
                }
                if(flagId == 0) {
                    System.out.println("not-found");
                }
                else {
                    int countAllBook = 0;
                    int countAllThesis = 0;
                    int countBorrowBook = 0;
                    int countBorrowThesis = 0;
                    for(Book it : book) {
                        if(it.library != null && it.library.equals(arr[0])) {
                            countAllBook += it.cteNumber;
                        }
                        if(it.library != null && it.library.equals(arr[0]) && it.cteNumber != it.number) {
                            countBorrowBook += it.cteNumber - it.number;
                        }

                    }
                    for(Thesis it : thesis) {
                        if(it.library != null && it.library.equals(arr[0])) {
                            countAllThesis += 1;
                        }
                        if(it.library != null && it.library.equals(arr[0]) && it.isBorrow) {
                            countBorrowThesis += 1;
                        }
                    }
                    System.out.println(countAllBook +" " + countAllThesis+" " + countBorrowBook + " " + countBorrowThesis);
                }
            }
            else if(order.toString().startsWith("report-passed-deadline#")) {
                order.delete(0,order.indexOf("#")+1);
                String[] arr = order.toString().split("[|]");
                TreeSet<String> resultOfSearch = new TreeSet<>();
                int flagId = 0 ;
                for(String s : library.keySet()) {
                    if(s.equals(arr[0])) {
                        flagId = 1;
                        break;
                    }
                }

                if(flagId == 0) {
                    System.out.println("not-found");
                }
                else if( library.get(arr[0]) != null && (!library.get(arr[0]).idBookOfBorrow.isEmpty() || !library.get(arr[0]).idThesisOfBorrow.isEmpty() || !borrow.isEmpty())) {
                    for(Borrow it : borrow){
                       int flagStudent = 0;
                       int flagStaff = 0;
                       int flagBook = 0;
                       int flagThesis = 0;
                       for(String studentKey : student.keySet()) {
                           if(studentKey.equals(it.idUser)) {
                               flagStudent = 1;
                               break;
                           }
                       }
                       for(String staffKey : staff.keySet()) {
                           if(staffKey.equals(it.idUser)) {
                               flagStaff = 1;
                               break;
                           }
                       }
                       for(Book bookKey : book) {
                           if(bookKey.idBook != null && bookKey.idBook.equals(it.idBook)) {
                               flagBook = 1;
                               break;
                           }
                       }
                       for(Thesis thesisKey : thesis) {
                           if(thesisKey.idThesis != null && thesisKey.idThesis.equals(it.idBook)) {
                               flagThesis = 1;
                               break;
                           }
                       }
                       String date = it.dateOfBorrow;
                       String time = it.clockOfBorrow;
                       double timeDef = 0 ;
                       if (flagStudent == 1) {
                           if (flagBook == 1) {
                               timeDef = (Main.calculateDateTimeDifference(arr[1], arr[2], date, time) - 10 * 24);

                           } else if (flagThesis == 1) {
                               timeDef = (Main.calculateDateTimeDifference(arr[1], arr[2], date, time) - 7 * 24);
                           }
                       }
                       if (flagStaff == 1) {
                           if (flagBook == 1) {
                               timeDef = (Main.calculateDateTimeDifference(arr[1], arr[2], date, time) - 14 * 24);

                           } else if (flagThesis == 1) {
                               timeDef = (Main.calculateDateTimeDifference(arr[1], arr[2], date, time) - 10 * 24);
                           }
                       }
                       int flag1 = 0;
                       for(String s : library.get(arr[0]).idBookOfBorrow){
                           if(it.idBook.equals(s)) {
                               flag1 = 1;
                               break;
                           }
                       }
                       int flag2 = 0 ;
                       for(String s : library.get(arr[0]).idThesisOfBorrow){
                           if(it.idBook.equals(s)) {
                               flag2 = 1;
                               break;
                           }
                       }
                       if(it.idLibrary.equals(arr[0]) && timeDef  > 0  && (flag2 == 1 || flag1 == 1) && it.fine == 0) {
                           resultOfSearch.add(it.idBook);
                       }
                   }

                    int i = 0 ;
                    if(!resultOfSearch.isEmpty()) {
                        for(String str : resultOfSearch) {

                            if(i < resultOfSearch.size() - 1) {
                                System.out.print(str+"|");
                            }
                            else{
                                System.out.println(str);
                            }
                            i++;
                        }
                    }
                    else{
                        System.out.println("none");
                    }
                }
                else{
                    System.out.println("none");
                }
            }
            else if(order.toString().startsWith("report-penalties-sum")) {
                System.out.println(Main.penalty);

            }
        }
    }

    private static void outputForSearch(TreeSet<String> resultOfSearch) {
        int i = 0 ;
        for(String str : resultOfSearch) {

            if(i < resultOfSearch.size() - 1 ) {
                System.out.print(str+"|");
            }
            else{
                System.out.println(str);
            }
            i++;
        }
    }

    private static void output(String result) {
        switch (result) {
            case "success" -> System.out.println("success");
            case "not-allowed" -> System.out.println("not-allowed");
            case "both" -> System.out.println("not-found");
            default -> System.out.println("not-found");
        }
    }
}


class Book {
    public String idBook;
    public String title;
    public String author;
    public String publisher;
    public String yearOfPublication;
    public int number;
    public  int cteNumber;
    public String category;
    public String library;

    public String AddBook(String order) {
        String[] arr = order.split("[|]");
        int flagLibrary = 0;
        int flagCategory = 0;
        if (!arr[6].equals("null")) {
            for (String s : Main.category.keySet()) {
                if (s.equals(arr[6])) {
                    flagCategory = 1;
                    break;
                }
            }
        }
        else{
            flagCategory = 1;
        }
        for (String s : Main.library.keySet()) {
            if (s.equals(arr[7])) {
                flagLibrary = 1;
                break;
            }
        }
        if (flagLibrary == 0 || flagCategory == 0) {
            return "not-found";
        }
        int flagDuolicate = 0 ;
        for (String s : Main.library.get(arr[7]).idBookOfLibrary) {
            if (s.equals(arr[0])) {
                flagDuolicate = 1;
            }
        }
        if(flagDuolicate == 1 && flagCategory == 0) {
            return "both";
        }
        else  if(flagCategory == 0){
            return "not-found";
        }
        else if(flagDuolicate == 1){
            return "duplicate-id";
        }
        idBook = arr[0];
        Main.library.get(arr[7]).idBookOfLibrary.add(arr[0]);
        title = arr[1];
        author = arr[2];
        publisher = arr[3];
        yearOfPublication = arr[4];
        number =Integer.parseInt( arr[5]);
        cteNumber = Integer.parseInt(arr[5]);
        category = arr[6];
        library = arr[7];

        return "success";
    }

    public String EditBook(String order) {
        String[] arr = order.split("[|]");
        int flagLibrary = 0;
        for(String s : Main.library.keySet()) {
            if (s.equals(arr[1])) {
                for(String it : Main.library.get(arr[1]).idBookOfLibrary) {
                    if(it.equals(arr[0])) {
                        flagLibrary = 1;
                        break;
                    }
                }
            }
        }
        Book book = null;
        for(Book it : Main.book) {
            if(it != null && it.idBook != null && it.idBook.equals(arr[0]) && it.library.equals(arr[1])) {
                book = it;
            }
        }
        int flagCategory = 0;
        if(!arr[7].equals( "-") && !arr[7].equals("null")) {
            for (String s : Main.category.keySet()) {
                if (s.equals(arr[7])) {
                    flagCategory = 1;
                }
            }
        }else{
            flagCategory = 1;
        }

        if (book != null && book.category != null && flagLibrary == 1 && flagCategory == 1) {

            if(!arr[2].equals("-")) {
                book.title = arr[2];
            }
            if(!arr[3].equals("-")) {
                book.author = arr[3];
            }
            if(!arr[4].equals("-")) {
                book.publisher = arr[4];
            }
            if(!arr[5].equals("-")) {
                book.yearOfPublication = arr[5];
            }
            if(!arr[6].equals("-")) {
                if(book.cteNumber > book.number) {
                    int difference = book.cteNumber - book.number;
                    book.number = Integer.parseInt(arr[6]) - difference;
                    book.cteNumber = Integer.parseInt(arr[6]);

                }
                else if(book.cteNumber == book.number) {
                    book.number =Integer.parseInt( arr[6]);
                    book.cteNumber =Integer.parseInt( arr[6]);
                }


            }
            if(!arr[7].equals("-")) {
                book.category = arr[7];


            }

            return "success";
        } else {
            return "not-found";
        }
    }
    public String RemoveBook(String order) {
        String[] arr = order.split("[|]");
        Book book = null;
        for(Book it : Main.book) {
            if(it != null && it.idBook != null && it.idBook.equals(arr[0]) && it.library.equals(arr[1])) {
                book = it;
            }
        }
        Library library = Main.library.get(arr[1]);
        int flag = 0;
        if(library != null) {
            for(String s : library.idBookOfBorrow){
                if(s.equals(arr[0])) {
                    flag = 1;
                    break;
                }
            }
        }

        if (book != null && book.library != null && book.library.equals(arr[1]) && flag == 0) {
            Main.library.get(arr[1]).idBookOfLibrary.remove(arr[0]);
            Main.library.get(arr[1]).idBookOfBorrow.remove(arr[0]);
            Main.book.remove(book);


            return "success";
        }
        else if(book != null && book.library != null && book.library.equals(arr[1]) && flag == 1){
            return "not-allowed";
        }
        else if((book != null && book.library!= null &&  !book.library.equals(arr[1])) && flag == 1){
            return "both";
        }
        else{
            return "not-found";
        }
    }
}
class Thesis {
    public  String idThesis;
    public String title;
    public String studentName;
    public String professorName;
    private String year;
    public String category;
    public String library;
    public boolean isBorrow;
    public String AddThesis(String order) {
        String[] arr = order.split("[|]");
        int flagLibrary = 0;
        int flagCategory = 0;
        if (!arr[5].equals("null")) {
            for (String s : Main.category.keySet()) {
                if (s.equals(arr[5])) {
                    flagCategory = 1;
                    break;
                }
            }
        }
        else{
            flagCategory = 1;
        }
        for (String s : Main.library.keySet()) {
            if (s.equals(arr[6])) {
                flagLibrary = 1;
                break;
            }
        }
        if (flagLibrary == 0 || flagCategory == 0) {
            return "not-found";
        }
        int flagDuolicate = 0 ;
        for (String s : Main.library.get(arr[6]).idThesisOfLibrary) {
            if (s.equals(arr[0])) {
                flagDuolicate = 1;
            }
        }
        if(flagDuolicate == 1 && flagCategory == 0) {
            return "both";
        }
        else  if(flagCategory == 0){
            return "not-found";
        }
        else if(flagDuolicate == 1){
            return "duplicate-id";
        }
        idThesis = arr[0];
        Main.library.get(arr[6]).idThesisOfLibrary.add(arr[0]);
        title = arr[1];
        studentName = arr[2];
        professorName = arr[3];
        year = arr[4];
        category = arr[5];
        library = arr[6];
        isBorrow = false;
        return "success";
    }
    public String EditThesis(String order) {
        String[] arr = order.split("[|]");
        Thesis thesis = null;
        for(Thesis it : Main.thesis) {
            if(it.idThesis != null && it.idThesis.equals(arr[0]) && it.library.equals(arr[1])) {
                thesis = it;
                break;
            }
        }
        int flagCategory = 0;
        if(!arr[6].equals( "-") && !arr[6].equals("null")) {
            for (String s : Main.category.keySet()) {
                if (s.equals(arr[6])) {
                    flagCategory = 1;
                }
            }
        }else{
            flagCategory = 1;
        }


        if (thesis != null && thesis.library.equals(arr[1]) && flagCategory == 1) {

            if(!arr[2].equals("-")) {
                thesis.title = arr[2];
            }
            if(!arr[3].equals("-")) {
                thesis.studentName = arr[3];
            }
            if(!arr[4].equals("-")) {
                thesis.professorName = arr[4];
            }
            if(!arr[5].equals("-")) {
                thesis.year = arr[5];
            }
            if(!arr[6].equals("-")) {
                thesis.category = arr[6];
            }

            return "success";
        } else {
            return "not-found";
        }
    }
    public String RemoveThesis(String order) {
        String[] arr = order.split("[|]");
        Thesis thesis = null;

        for(Thesis it : Main.thesis) {
            if(it.idThesis != null && it.idThesis.equals(arr[0]) && it.library.equals(arr[1])) {
                thesis = it;
                break;
            }
        }
        int flag = 0 ;
        Library library = Main.library.get(arr[1]);
        if(library != null) {
            for(String s : library.idThesisOfBorrow){
                if(s.equals(arr[0])) {
                    flag = 1;
                }
            }
        }
        if (thesis != null && thesis.library != null && thesis.library.equals(arr[1]) && flag == 0) {
            Main.library.get(arr[1]).idThesisOfLibrary.remove(arr[0]);
            Main.library.get(arr[1]).idThesisOfBorrow.remove(arr[0]);
            int check = 0 ;
            for(Library it : Main.library.values()){
                for(String s : it.idThesisOfLibrary){
                    if(s.equals(arr[0])) {
                        check = 1;
                    }
                }
            }
            if(check == 0){
                Main.thesis.remove(thesis);

            }
            return "success";
        }
        else if(thesis != null && thesis.library != null && thesis.library.equals(arr[1]) && flag == 1){
            return "not-allowed";
        }
        else if((thesis != null && thesis.library != null &&  !thesis.library.equals(arr[1])) && flag == 1){
            return "both";
        }
        else{
            return "not-found";
        }
    }
}
class Student{
    public String studentNumber;
    public String password;
    public String firstName;
    public String lastName;
    private String nationalCode;
    private String yearOfBirth;
    private String address;
    public int RestrictionsBorrowing;
    public HashMap<String , Boolean> reserveSeat = new HashMap<String, Boolean>();
    public String AddStudent(String order) {
        String[] arr = order.split("[|]");
        Student student = Main.student.get(arr[0]);
        if (student != null && student.studentNumber != null) {
            for(String s : Main.student.keySet()){
                if (student.studentNumber.equals(s)) {
                    return "duplicate-id";
                }
            }
        }
        studentNumber = arr[0];
        password = arr[1];
        firstName = arr[2];
        lastName = arr[3];
        nationalCode = arr[4];
        yearOfBirth = arr[5];
        address = arr[6];
        RestrictionsBorrowing = 0 ;
        return "success";
    }
    public String EditStudent(String order) {
        String[] arr = order.split("[|]");
        Student student = Main.student.get(arr[0]);
        if (student != null && student.studentNumber != null) {
            for(String s : Main.student.keySet()){
                if (student.studentNumber.equals(s)) {
                    if(!arr[1].equals("-")) {
                        Main.student.get(arr[0]).password = arr[1];
                    }
                    if(!arr[2].equals("-")) {
                        Main.student.get(arr[0]).firstName = arr[2];
                    }
                    if(!arr[3].equals("-")) {
                        Main.student.get(arr[0]).lastName = arr[3];
                    }
                    if(!arr[4].equals("-")) {
                        Main.student.get(arr[0]).nationalCode = arr[4];
                    }
                    if(!arr[5].equals("-")) {
                        Main.student.get(arr[0]).yearOfBirth = arr[5];
                    }
                    if(!arr[6].equals("-")) {
                        Main.student.get(arr[0]).address = arr[6];
                    }

                    return "success";
                }
            }
        }

        return "not-found";


    }


    public String RemoveStudent(String order) {
        String[] arr = order.split("[|]");
        Student student = Main.student.get(arr[0]);
        int flag = 0 ;
        for (Borrow it : Main.borrow) {
            if (it.idUser.equals(student.studentNumber)) {
                flag = 1;
                break;
            }
        }
        if (student != null && student.studentNumber != null && student.studentNumber.equals(arr[0]) && flag == 0) {
            Main.student.remove(arr[0]);
            return "success";
        }
        else if(student != null && flag == 1){
            return "not-allowed";
        }
        else{
            return "not-found";
        }
    }
}
class Staff {
    public String id;
    public String password;
    public String firstName;
    public String lastName;
    private String nationalCode;
    private String yearOfBirth;
    private String address;
    public int RestrictionsBorrowing;
    public HashMap<String , Boolean> reserveSeat = new HashMap<String, Boolean>();
    public String AddStaff(String order) {
        String[] arr = order.split("[|]");
        Staff staff = Main.staff.get(arr[0]);
        if (staff != null && staff.id != null) {
            for(String s : Main.staff.keySet()){
                if (staff.id.equals(s)) {
                    return "duplicate-id";
                }
            }
        }
        id = arr[0];
        password = arr[1];
        firstName = arr[2];
        lastName = arr[3];
        nationalCode = arr[4];
        yearOfBirth = arr[5];
        address = arr[6];
        RestrictionsBorrowing = 0 ;

        return "success";
    }
    public String EditStaff(String order) {
        String[] arr = order.split("[|]");
        Staff staff = Main.staff.get(arr[0]);
        if (staff != null && staff.id != null) {
            for(String s : Main.staff.keySet()){
                if (staff.id.equals(s)) {
                    if(!arr[1].equals("-")) {
                        Main.staff.get(arr[0]).password = arr[1];
                    }
                    if(!arr[2].equals("-")) {
                        Main.staff.get(arr[0]).firstName = arr[2];
                    }
                    if(!arr[3].equals("-")) {
                        Main.staff.get(arr[0]).lastName = arr[3];
                    }
                    if(!arr[4].equals("-")) {
                        Main.staff.get(arr[0]).nationalCode = arr[4];
                    }
                    if(!arr[5].equals("-")) {
                        Main.staff.get(arr[0]).yearOfBirth = arr[5];
                    }
                    if(!arr[6].equals("-")) {
                        Main.staff.get(arr[0]).address = arr[6];
                    }
                    return "success";
                }
            }
        }
        return "not-found";
    }
    public String RemoveStaff(String order) {
        String[] arr = order.split("[|]");
        Staff staff = Main.staff.get(arr[0]);
        int flag = 0 ;
        for (Borrow it : Main.borrow) {
            if (it.idUser.equals(staff.id)) {
                flag = 1;
                break;
            }
        }
        if (staff != null && staff.id != null && flag == 0) {
            Main.staff.remove(arr[0]);
            return "success";
        }
        else if (staff != null && flag == 1) {
            return "not-allowed";
        }
        else {
            return "not-found";
        }
    }
}
class Category{
    public final static ArrayList<String> idCategory = new ArrayList<String>();
    public int number;
    private  String name;
    /**
     * Pay attention to the added categories in general
     * and is not related to only one library

     */
    public  String AddCategory(String order){
        String[] arr = order.split("[|]");
        for(String s : idCategory) {
            if(arr[0].equals(s)) {
                return "duplicate-id";
            }
        }
        idCategory.add(arr[0]);
        name = arr[1];
        return "success";
    }
}
class Library{
    public  ArrayList<String> idLibrary = new ArrayList<String>() ;
    public  ArrayList<String> idBookOfLibrary = new ArrayList<String>();
    public ArrayList<String> idThesisOfLibrary = new ArrayList<String>();
    public ArrayList<String> idBookOfBorrow = new ArrayList<String>();
    public ArrayList<String> idThesisOfBorrow = new ArrayList<String>();
    public ArrayList<ReserveSeat> reserveSeats = new ArrayList<>();
    private  String name;
    private  int yearOfEstablishment;
    public  int NumberOfTables;
    private  String address;
    public  String AddLibrary(String order) {
        String[] arr = order.split("[|]");
        for(String s : idLibrary) {
            if(arr[0].equals(s)) {
                return "duplicate-id";
            }
        }
        idLibrary.add(arr[0]);
        name = arr[1];
        yearOfEstablishment = Integer.parseInt(arr[2]);
        NumberOfTables = Integer.parseInt(arr[3]);
        address = arr[4];
        return "success";

    }
}
class Borrow{
    public String idUser;
    public String password;
    public String idLibrary;
    public String idBook;
    public String dateOfBorrow;
    public String clockOfBorrow;
    public long fine ;
    public Borrow(String idUser, String password, String idLibrary, String idBook,String dateOfBorrow, String clockOfBorrow) {
        this.idUser = idUser;
        this.password = password;
        this.idLibrary = idLibrary;
        this.idBook = idBook;
        this.clockOfBorrow = clockOfBorrow;
        this.dateOfBorrow = dateOfBorrow;
        fine = 0;
    }
}

class ReserveSeat{
    public String idUser;
    private String password;
    private String idLibrary;
    public String dete;
    public String reserveTime;
    public String deliveryTime;
    public int number;
    public ReserveSeat(String idUser, String password, String idLibrary, String dete, String reserveTime, String deliveryTime) {
        this.idUser = idUser;
        this.password = password;
        this.idLibrary = idLibrary;
        this.dete = dete;
        this.reserveTime = reserveTime;
        this.deliveryTime = deliveryTime;
        int coint = 0 ;
        for(ReserveSeat it : Main.library.get(idLibrary).reserveSeats) {
            if(it.dete.equals(dete) && it.idLibrary.equals(idLibrary)) {
                coint += 1;
            }
        }
        number = Main.library.get(idLibrary).NumberOfTables - coint-1;
    }
}