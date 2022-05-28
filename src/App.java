import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
// import java.util.HashMap; Flyweight

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

/*
Interface of Flyweigt Pattern
interface Todo {
    public String toString();
}

class TodoFactory {
    private static final HashMap<String, Task> taskMap = new HashMap<>();

    public static Todo getTask(String title) {
        Task task = (Task)taskMap.get(title);

        if (task == null) {
            task = new Task(title);
            taskMap.put(title, task);
        }

        return task ;
    }
}
*/

// Observer Pattern
interface Todo {
    void subscribe(Subtodo subtodo);
    void unsubscribe(Subtodo subtodo);
    void notifySubtodo(String msg);
}

class Task implements Todo {
    private int num ;
    private String title ;
    private String backgroundColor ;
    private ArrayList<Subtask> subtaskList ;
    private ArrayList<Subtodo> subtodos = new ArrayList<>() ; // Observer Pattern
    private TaskState taskState; // State Pattern

    public Task () {
        
    }

    public Task (String title) {
        this.title = title ;
        this.backgroundColor = "White" ;
        this.taskState = new Waiting(); // State Pattern
        this.subtaskList = new ArrayList<Subtask>();
    }

    public ArrayList<Subtask> getSubtask() {
        return subtaskList;
    }

    public void setSutbtask(String title, int num) {
        Subtask st = new Subtask() ;
        st.setTitle(title);
        st.setNum(num);
        st.setState("대기중");
        this.subscribe(st); // Observer Pattern
        this.subtaskList.add(st);
        this.subtaskList.sort(Comparator.comparing(Subtask::getNum));
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

    public String getBackgroundColor() {
        return backgroundColor ;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor ;
    }

    // State Pattern
    public void setTaskState(TaskState tastState) {
        this.taskState = new OnGoing() ;
    }

    // State Pattern
    public String stateChange() {
        return taskState.stateChange();
    }

    // Observer Patter
    public void upgradeComplete() {
        this.backgroundColor = "Blue" ;
        // this.state = "완료" ;
        this.taskState = new Complete();
        notifySubtodo("완료");
    }
    
    // Observer Patter
    @Override
    public void subscribe(Subtodo subtodo) {
        subtodos.add(subtodo);
    }

    // Observer Patter
    @Override
    public void unsubscribe(Subtodo subtodo) {
        subtodos.remove(subtodo);
    }

    // Observer Patter
    @Override
    public void notifySubtodo(String msg) {
        subtodos.forEach(crew -> crew.update(msg));
    }

    @Override
    public String toString() {
        return "[" + this.getNum() + "] Task : " + this.getTitle() + " (상태 : " + this.taskState.stateChange() + "/배경색 : " + this.getBackgroundColor() + ")" + "\nSubtask : " + this.getSubtask() + "\n";
    }
}

interface Subtodo {
    void update(String msg);
}

class Subtask implements Subtodo {
    private int num ;
    private String title ;
    private String state ;

    public Subtask () {
        
    }

    public Subtask (String title) {
        this.title = title ;
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

    public String getState() {
        return state ;
    }

    public void setState(String state) {
        this.state = state ;
    }

    // Obeserver Pattern
    @Override
    public void update(String msg) {
        this.setState(msg);
    }
	
    @Override
    public String toString() {
        return "\n" + this.getNum() + ".Subtask : " + this.getTitle() + " | 상태 : " + this.getState() ;
    }
}

// State Pattern
interface TaskState {
    public String stateChange() ;
}

// State Pattern
class Waiting implements TaskState {
    public String stateChange() {
        return "대기중";
    }
}

// State Pattern
class OnGoing implements TaskState {
    public String stateChange() {
        return "진행중";
    }
}

// State Pattern
class Complete implements TaskState {
    public String stateChange() {
        return "완료";
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

    public boolean menuChoose(String input) {
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

    private void dataRead() {
        boolean check = true ;
        try {
            if (list.size() == 0) {
                System.out.println("");
                System.out.println("입력된 Task가 없습니다.");
                System.out.println("");
            } else {
                System.out.println("");
                for (Task p: this.list) {
                    System.out.println(p.toString());
                }
                while (check) {
                    System.out.println("");
                    System.out.println("1. Task 상세조회");
                    System.out.println("2. 조회 종료");
                    System.out.print("번호 입력 : ");
                    br = new BufferedReader(new InputStreamReader(System.in));
                    String taskChoise = br.readLine() ;
                    
                    switch (taskChoise) {
                        case "1" :
                            taskDetail();
                            break ;
                        case "2" :
                            check = false ;
                            break ;
                        default :
                            break ;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    private void dataSave() {
        // Task t = (Task)TodoFactory.getTask(""); Flywaight
        Task t = new Task("");
        boolean check = true ;

        try {
            System.out.println("");
            System.out.print("Task Title 입력 : ");
            br = new BufferedReader(new InputStreamReader(System.in));
            t.setTitle(br.readLine());
            t.setNum(this.list.size());

            while(check) {
                System.out.println("");
                System.out.println("1. SubTask 입력");
                System.out.println("2. 입력 완료");
                System.out.print("번호 입력 : ");
                br = new BufferedReader(new InputStreamReader(System.in));
                String subtaskInput = br.readLine() ;

                switch (subtaskInput) {
                    case "1" :
                        System.out.println("");
                        System.out.print("Subtask Title 입력 : ");
                        br = new BufferedReader(new InputStreamReader(System.in));
                        t.setSutbtask(br.readLine(), t.getSubtask().size());
                        break ;
                    case "2" :
                        System.out.println("");
                        System.out.println("[SAVE] Subtask");
                        check = false ;
                        break ;
                    default :
                        System.out.println("");
                        System.out.println("[ERROR] Retry");
                        break ;
                }
            }
            this.list.add(t);
            this.list.sort(Comparator.comparing(Task::getNum));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void dataEdit() {
        try {
            System.out.print("선택할 번호 입력 : ");
            br = new BufferedReader(new InputStreamReader(System.in));
            int num = Integer.parseInt(br.readLine());
            if (valid(num)) {
                System.out.print("Task Title 입력 : ");
                br = new BufferedReader(new InputStreamReader(System.in));
                this.list.get(num).setTitle(br.readLine());
            } else {
                dataEdit();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void taskDetail() {
        boolean check = true ;

        try {
            System.out.println("");
            System.out.print("조회 할 Task 번호 입력 : ");
            br = new BufferedReader(new InputStreamReader(System.in));
            int num = Integer.parseInt(br.readLine());
            if (valid(num)) {
                System.out.println("");
                System.out.println("Task : " + this.list.get(num).getTitle());
                System.out.println("SubTask");
                System.out.println(this.list.get(num).getSubtask());
                System.out.println("");

                while (check) {
                    System.out.println("");
                    System.out.println("1. Task 상태수정");
                    System.out.println("2. Subtask 상태수정");
                    System.out.println("3. 조회 종료");
                    System.out.print("번호 입력 : ");
                    br = new BufferedReader(new InputStreamReader(System.in));
                    String subtaskChoise = br.readLine() ;
                    
                    switch (subtaskChoise) {
                        case "1" :
                            checkingTask(num);
                            break ;
                        case "2" :
                            checkingSubtask(num);
                            break ;
                        case "3" :
                            check = false ;
                            break ;
                        default :
                            break ;
                    }
                }

            } else {
                dataEdit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkingTask(int num) {
        try {
            System.out.println("");
            if (valid(num)) {
                OnGoing onGoing = new OnGoing();

                System.out.println("1. 진행중");
                System.out.println("2. 완료");
                System.out.println("3. 취소");
                System.out.print("번호 입력 : ");
                br = new BufferedReader(new InputStreamReader(System.in));
                String stateChange = br.readLine() ;
            
                switch (stateChange) {
                    case "1" :
                        this.list.get(num).setTaskState(onGoing); // State Pattern
                        break ;
                    case "2" :
                        this.list.get(num).upgradeComplete();; // Observer Pattern
                        break ;
                    case "3" :
                        break ;
                }
            } else {
                checkingTask(num);
            }
        } catch (Exception e) {
            //TODO: handle exception
        }
    }

    private void checkingSubtask(int num) {
        try {
            System.out.println("");
            System.out.print("Subtask 선택 : ");
            br = new BufferedReader(new InputStreamReader(System.in));
            int subNum = Integer.parseInt(br.readLine());
            if (validSubtask(num, subNum)) {
                System.out.println("1. 진행중");
                System.out.println("2. 완료");
                System.out.println("3. 취소");
                System.out.print("번호 입력 : ");
                br = new BufferedReader(new InputStreamReader(System.in));
                String stateChange = br.readLine() ;

            
                switch (stateChange) {
                    case "1" :
                        this.list.get(num).getSubtask().get(subNum).setState("진행중");
                        break ;
                    case "2" :
                        this.list.get(num).getSubtask().get(subNum).setState("완료");
                        break ;
                    case "3" :
                        break ;
                }
            } else {
                checkingSubtask(num);
            }
        } catch (Exception e) {
            //TODO: handle exception
        }
    }

    private boolean valid(int num) {
        if (this.list.size() <= num) {
            System.err.println("없는 번호 입니다.");
            return false ;
        }

        return true;
    }

    private boolean validSubtask(int num, int subNum) {
        if (this.list.get(num).getSubtask().size() <= subNum) {
            System.err.println("없는 번호 입니다.");
            return false ;
        }

        return true ;
    }
}