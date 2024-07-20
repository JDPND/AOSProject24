import java.util.Random;

public class FirstComeFirstServe {
    private static final int NUM_PROCESSES = 79;
    private static final int MAX_TRANSIENT_EVENTS = 10;
    private static final Random RANDOM = new Random();

    static class Process {
        String name;
        int burstTime;
        int waitingTime;
        int turnaroundTime;

        Process(String name, int burstTime) {
            this.name = name;
            this.burstTime = burstTime;
        }
    }

    public static void main(String[] args) {
        Process[] processes = generateProcesses();
        introduceTransientEvents(processes);
        calculateTimes(processes);
        printResults(processes);
    }

    private static Process[] generateProcesses() {
        Process[] processes = new Process[NUM_PROCESSES];
        for (int i = 0; i < NUM_PROCESSES; i++) {
            processes[i] = new Process("P" + (i + 1), (i % 10) + 1);
        }
        return processes;
    }

    private static void introduceTransientEvents(Process[] processes) {
        int numTransientEvents = RANDOM.nextInt(MAX_TRANSIENT_EVENTS) + 1;
        System.out.println("\nNumber of transient events: " + numTransientEvents);
        for (int i = 0; i < numTransientEvents; i++) {
            int processIndex = RANDOM.nextInt(NUM_PROCESSES);
            int adjustment = RANDOM.nextInt(5) + 1;
            processes[processIndex].burstTime += adjustment;
            System.out.println("Transient event: Process " + processes[processIndex].name +
                    " burst time adjusted by " + adjustment + " units");
        }
    }

    private static void calculateTimes(Process[] processes) {
        int currentTime = 0;
        for (Process process : processes) {
            process.waitingTime = currentTime;
            currentTime += process.burstTime;
            process.turnaroundTime = currentTime;
        }
    }

    private static void printResults(Process[] processes) {
        System.out.println("\nProcess\tBurst Time\tWaiting Time\tTurnaround Time");
        double totalWaitingTime = 0;
        double totalTurnaroundTime = 0;

        for (Process process : processes) {
            System.out.printf("%s\t\t%d\t\t\t%d\t\t\t\t%d\n",
                    process.name, process.burstTime, process.waitingTime, process.turnaroundTime);
            totalWaitingTime += process.waitingTime;
            totalTurnaroundTime += process.turnaroundTime;
        }

        double avgWaitingTime = totalWaitingTime / NUM_PROCESSES;
        double avgTurnaroundTime = totalTurnaroundTime / NUM_PROCESSES;

        System.out.printf("\nAverage waiting time = %.2f\n", avgWaitingTime);
        System.out.printf("Average turnaround time = %.2f\n", avgTurnaroundTime);
    }
}