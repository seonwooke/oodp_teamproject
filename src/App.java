import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class App {
    public static void main (String[] args) {
        Menu m = new Menu() ;
        boolean check = true ;
        while (check) {
            try {
                m.printMenu() ;
                BufferedReader sbr = new BufferedReader(new InputStreamReader(System.in)) ;
                String input = sbr.readLine() ;
                check = m.menuChoose(input) ;
            } catch (IOException e) {
                e.printStackTrace() ;
            }
        }
    }
}

class Task {
    private int num ;
    private String title ;
    class Subtask {
        private int num ;
        private String title ;


        public Subtask (String title) {
            this.title = title ;
        }

        public Subtask () {

        }

        public String getTitle() {
           return title ;
        }

        public void setTitle(String title) {
            this.title = title ;
        }

        public int getNum() {
            return num ;
        }

        public void setNum(int num) {
            this.num = num ;
        }

        @Override
        public String toString() {
            return "num : " + this.getNum() + ", Title : " + this.getTitle() ;
        }
    }

    public Task (String title) {
        this.title = title ;
    }

    public Task () {

    }

    public String getTitle() {
        return title ;
    }

    public void setTitle(String title) {
        this.title = title ;
    }

    public int getNum() {
        return num ;
    }

    public void setNum(int num) {
        this.num = num ;
    }

    @Override
    public String toString() {
        return "num : " + this.getNum() + ", Title : " + this.getTitle() ;
    }
}

class Menu {
    BufferedReader br ;
    private List<Task> list ;

    public Menu() {
        this.list = new ArrayList<>();
    }

    public void printMenu() {
        System.out.println("--Task--");
        System.out.println("1. 조회");
        System.out.println("2. 입력");
        System.out.println("3. 수정");
        System.out.println("4. 삭제");
        System.out.println("5. 종료");
        System.out.println("-------");
    }

    public  boolean menuChoose(String input) {
        switch (input) {
            case "1" :
                    dataRead();
                break;
            case "2" :
                    dataSave();
                break;
            case "3" :
                    dataEdit();
                break;
            case "4" :
                    dataDel();
                break;
            case "5" :
                System.out.println("Quit");
                    return false;
            default :
                System.out.println("[ERROR] Retry");
                
        }
        return true;
    }

    private void dataDel() {
        System.out.println("삭제할 번호 입력");
        br = new BufferedReader(new InputStreamReader(System.in));
        try {
            int num = Integer.parseInt(br.readLine());
            if (valid(num)) {
                this.list.remove(num);
            } else {
                dataDel();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void dataEdit() {
        try {
            System.out.println("선택할 번호 입력");
            br = new BufferedReader(new InputStreamReader(System.in));
            int num = Integer.parseInt(br.readLine());
            if (valid(num)) {
                System.out.println("Task Title 입력");
                br = new BufferedReader(new InputStreamReader(System.in));
                this.list.get(num).setTitle(br.readLine());
            } else {
                dataEdit();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void dataSave() {
        Task t = new Task();
        try {
            System.out.println("Task Title 입력");
            br = new BufferedReader(new InputStreamReader(System.in));
            t.setTitle(br.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        t.setNum(this.list.size());
        this.list.add(t);
        this.list.sort(Comparator.comparing(Task::getNum));
    }

    private void dataRead() {
        System.out.println("조회 시작");
        for (Task p: this.list) {
            System.out.println(p.toString());
        }
        System.out.println("조회 끝");
    }

    private boolean valid(int num) {
        if (this.list.size() <= num) {
            System.err.println("없는 번호 입니다.");
            return false ;
        }

        return true;
    }
}