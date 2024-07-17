import java.util.*;
class Processs {
    int id;
    int arrivalTime;
    int burstTime;
    int remainingTime;

    Processs(int id, int arrivalTime, int burstTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
    }
}

public class SRTFScheduling {
    public static void srtfScheduling(List<Processs> processses) {
        processses.sort(Comparator.comparingInt(p -> p.arrivalTime));
        int n = processses.size();
        int complete = 0;
        int currentTime = 0;
        int minRemainingTime = Integer.MAX_VALUE;
        int shortest = -1;
        boolean check = false;
        int[] waitingTimes = new int[n];
        int[] turnaroundTimes = new int[n];

        while (complete != n) {
            for (int j = 0; j < n; j++) {
                if ((processses.get(j).arrivalTime <= currentTime) &&
                        (processses.get(j).remainingTime < minRemainingTime) &&
                        processses.get(j).remainingTime > 0) {
                    minRemainingTime = processses.get(j).remainingTime;
                    shortest = j;
                    check = true;
                }
            }

            if (!check) {
                currentTime++;
                continue;
            }

            processses.get(shortest).remainingTime--;
            minRemainingTime = processses.get(shortest).remainingTime;

            if (minRemainingTime == 0) {
                minRemainingTime = Integer.MAX_VALUE;
            }

            if (processses.get(shortest).remainingTime == 0) {
                complete++;
                check = false;

                int finishTime = currentTime + 1;
                waitingTimes[shortest] = finishTime - processses.get(shortest).burstTime - processses.get(shortest).arrivalTime;

                if (waitingTimes[shortest] < 0) {
                    waitingTimes[shortest] = 0;
                }
            }

            currentTime++;
        }

        for (int i = 0; i < n; i++) {
            turnaroundTimes[i] = processses.get(i).burstTime + waitingTimes[i];
        }

        System.out.println("Process\tBurst Time\tArrival Time\tWaiting Time\tTurnaround Time");
        for (int i = 0; i < n; i++) {
            System.out.println("P" + (processses.get(i).id + 1) + "\t\t\t" + processses.get(i).burstTime + "\t\t\t" + processses.get(i).arrivalTime + "\t\t\t" + waitingTimes[i] + "\t\t\t\t" + turnaroundTimes[i]);
        }

        double avgWaitingTime = Arrays.stream(waitingTimes).average().orElse(0.0);
        double avgTurnaroundTime = Arrays.stream(turnaroundTimes).average().orElse(0.0);

        System.out.printf("\nAverage waiting time = %.2f\n", avgWaitingTime);
        System.out.printf("Average turnaround time = %.2f\n", avgTurnaroundTime);
    }

    public static void main(String[] args) {
        List<Processs> processses = new ArrayList<>();
        Random rand = new Random(0);  // For reproducibility

        for (int i = 0; i < 79; i++) {
            int arrivalTime = rand.nextInt(11);  // Random arrival time between 0 and 10
            int burstTime = rand.nextInt(10) + 1;  // Random burst time between 1 and 10
            processses.add(new Processs(i, arrivalTime, burstTime));
        }

        srtfScheduling(processses);
    }
}