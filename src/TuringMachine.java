import java.util.*;

public class TuringMachine {

    public static Map<Status, Transition> TransitionsSet = new HashMap<>();
    static Status status;
    static int head;
    static HashSet<String> finalStates = new HashSet<>();
    static StringBuilder tape = new StringBuilder();


    public static void main(String[] args) {
        Scanner scanner = getTransitions();

        getFinalStates(scanner);
        getInput(scanner);
        machineExecution();
        printOutput();


    }

    public static void getInput(Scanner scanner) {
        System.out.println("Please input the content of input-tape:");
        tape.append(scanner.nextLine());
        status = new Status("q0", tape.charAt(0));
        head = 0;
    }

    public static void getFinalStates(Scanner scanner) {
        System.out.println("Please input final states:");
        finalStates.addAll(Arrays.asList(scanner.nextLine().replace(" ", "").split("[,=-]")));
    }

    public static void printOutput() {
        System.out.println("Tape:");
        System.out.println(tape.toString() + "\n");

        if (finalStates.contains(status.getState()))
            System.out.println("Accepted!\nMachine halted in a final state!");
        else
            System.out.println("Not Accepted!\nMachine halted in a non-final state!");
    }

    public static void machineExecution() {
        while (TransitionsSet.get(status) != null) {

            Transition com = TransitionsSet.get(status);

            tape.setCharAt(head, com.getReplace());

            if (com.getDirection() == 'R' || com.getDirection() == 'r') {
                head += 1;
                if (tape.length() <= head) tape.append('□');

            } else {
                head -= 1;
                if (head < 0) {
                    tape.insert(0, '□');
                    head = 0;
                }

            }
            status = new Status(com.state, tape.charAt(head));


        }
    }

    public static Scanner getTransitions() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please input the transitions:");
        String transition = scanner.nextLine();


        while (!transition.equals("end")) {
            String[] parse = transition.replace(" ", "").split("[,=-]");
            Status s = new Status(parse[0], parse[1].charAt(0));
            Transition c = new Transition(parse[2], parse[3].charAt(0), parse[4].charAt(0));
            TransitionsSet.put(s, c);
            transition = scanner.nextLine();
        }
        return scanner;
    }


    static class Status {
        String state;
        Character input;

        public Status(String state, Character input) {
            this.state = state;
            this.input = input;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Status status = (Status) o;
            return Objects.equals(state, status.state) &&
                    Objects.equals(input, status.input);
        }

        @Override
        public int hashCode() {
            return Objects.hash(state, input);
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public Character getInput() {
            return input;
        }

        public void setInput(Character input) {
            this.input = input;
        }
    }

    static class Transition {
        private final String state;
        private final char replace;
        private final char direction;

        public Transition(String state, char replace, char direction) {
            this.state = state;
            this.replace = replace;
            this.direction = direction;
        }

        public String getState() {
            return state;
        }

        public char getReplace() {
            return replace;
        }

        public char getDirection() {
            return direction;
        }
    }


}
