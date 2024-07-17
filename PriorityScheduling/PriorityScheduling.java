import java.util.*;

class NoOfProcess {
    String name;
    int burstTime;
    int priority;

    NoOfProcess(String name, int burstTime, int priority) {
        this.name = name;
        this.burstTime = burstTime;
        this.priority = priority;
    }
}

public class PriorityScheduling {

    public static void priorityScheduling(List<NoOfProcess> noOfProcesses) {
        noOfProcesses.sort(Comparator.comparingInt(p -> p.priority));
        int waitingTime = 0;
        int totalWaitingTime = 0;
        int currentTime = 0;
        int turnaroundTime = 0;
        int totalTurnaroundTime = 0;

        System.out.println("Process\tBurst Time\tPriority\tWaiting Time\tTurnaround Time");

        for (NoOfProcess noOfProcess : noOfProcesses) {
            waitingTime = currentTime;
            totalWaitingTime += waitingTime;
            turnaroundTime = waitingTime + noOfProcess.burstTime;
            totalTurnaroundTime += turnaroundTime;
            currentTime += noOfProcess.burstTime;

            System.out.println(noOfProcess.name + "\t\t\t" + noOfProcess.burstTime + "\t\t\t" + noOfProcess.priority + "\t\t\t\t" + waitingTime + "\t\t\t\t" + turnaroundTime);
        }

        System.out.printf("\nAverage waiting time = %.2f\n", (double) totalWaitingTime / noOfProcesses.size());
        System.out.printf("Average turnaround time = %.2f\n", (double) totalTurnaroundTime / noOfProcesses.size());
    }

    public static void main(String[] args) {
        List<NoOfProcess> noOfProcesses = new ArrayList<>();
        for (int i = 1; i <= 79; i++) {
            noOfProcesses.add(new NoOfProcess("P" + i, (int) (Math.random() * 10 + 1), (int) (Math.random() * 10 + 1)));
        }

        priorityScheduling(noOfProcesses);
    }
}
