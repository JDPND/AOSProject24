import java.util.*;
class NumOfProcess {
    String name;
    int burstTime;

    NumOfProcess(String name, int burstTime) {
        this.name = name;
        this.burstTime = burstTime;
    }
}

public class SJFScheduling {
    public static void main(String[] args) {
        List<NumOfProcess> numOfProcesses = new ArrayList<>();
        Random random = new Random();

        // Generating 79 processes with random burst times between 1 and 10 for demonstration
        for (int i = 1; i <= 79; i++) {
            numOfProcesses.add(new NumOfProcess("P" + i, random.nextInt(10) + 1));
        }

        sjfScheduling(numOfProcesses);
    }

    public static void sjfScheduling(List<NumOfProcess> numOfProcesses) {
        // Sort the processes based on their burst time
        Collections.sort(numOfProcesses, Comparator.comparingInt(p -> p.burstTime));

        int currentWaitingTime = 0;
        int totalWaitingTime = 0;
        int currentTurnaroundTime = 0;
        int totalTurnaroundTime = 0;
        int currentTime = 0;

        System.out.println("Process\tBurst Time\tWaiting Time\tTurnaround Time");

        for (NumOfProcess numOfProcess : numOfProcesses) {
            currentWaitingTime = currentTime;
            totalWaitingTime += currentWaitingTime;
            currentTurnaroundTime = currentWaitingTime + numOfProcess.burstTime;
            totalTurnaroundTime += currentTurnaroundTime;
            currentTime += numOfProcess.burstTime;
            System.out.println(numOfProcess.name + "\t\t" + numOfProcess.burstTime + "\t\t\t" + currentWaitingTime + "\t\t\t\t" + currentTurnaroundTime);
        }

        // Calculate and print average waiting time and turnaround time
        System.out.printf("\nAverage waiting time = %.2f\n", (double) totalWaitingTime / numOfProcesses.size());
        System.out.printf("Average turnaround time = %.2f\n", (double) totalTurnaroundTime / numOfProcesses.size());
    }
}
