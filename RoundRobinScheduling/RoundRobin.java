import java.util.*;

public class RoundRobin {
    private static class Process {
        String name;
        int burstTime;
        int remainingTime;

        Process(String name, int burstTime) {
            this.name = name;
            this.burstTime = burstTime;
            this.remainingTime = burstTime;
        }
    }

    public static void main(String[] args) {
        final int NUM_PROCESSES = 79;
        final int TIME_QUANTUM = 2;
        final int MAX_TRANSIENT_EVENTS = 10; // Maximum number of transient events

        // Initialize processes
        Queue<Process> processQueue = new LinkedList<>();
        Random random = new Random();
        for (int i = 0; i < NUM_PROCESSES; i++) {
            processQueue.add(new Process("P" + (i + 1), (i % 10) + 1));
        }

        // Introduce transient events
        int numTransientEvents = random.nextInt(MAX_TRANSIENT_EVENTS) + 1;
        System.out.println("Number of transient events: " + numTransientEvents);
        for (int i = 0; i < numTransientEvents; i++) {
            int index = random.nextInt(NUM_PROCESSES);
            int adjustment = random.nextInt(5) + 1; // Adjust burst time by 1 to 5 units
            List<Process> processes = new ArrayList<>(processQueue);
            Process transientProcess = processes.get(index);
            transientProcess.burstTime += adjustment;
            transientProcess.remainingTime += adjustment;
            System.out.println("Transient event: Process " + transientProcess.name + " burst time adjusted by " + adjustment + " units");
        }

        // Reset queue with updated burst times
        processQueue = new LinkedList<>(new ArrayList<>(processQueue));

        // Round Robin scheduling
        int currentTime = 0;
        int[] waitingTimes = new int[NUM_PROCESSES];
        int[] turnaroundTimes = new int[NUM_PROCESSES];
        int processedCount = 0;

        // Track the original process order to compute waiting times later
        Map<String, Integer> processIndexMap = new HashMap<>();
        int index = 0;
        for (Process p : processQueue) {
            processIndexMap.put(p.name, index++);
        }

        // Round Robin Scheduling
        while (!processQueue.isEmpty()) {
            Process process = processQueue.poll();
            int timeToRun = Math.min(process.remainingTime, TIME_QUANTUM);
            process.remainingTime -= timeToRun;
            currentTime += timeToRun;

            if (process.remainingTime > 0) {
                processQueue.add(process); // Re-add process if it still needs more time
            } else {
                int processIndex = processIndexMap.get(process.name);
                waitingTimes[processIndex] = currentTime - process.burstTime;
                turnaroundTimes[processIndex] = waitingTimes[processIndex] + process.burstTime;
                processedCount++;
            }
        }

        // Print results
        System.out.println("\nProcess\tBurst Time\tWaiting Time\tTurnaround Time");
        for (int i = 0; i < NUM_PROCESSES; i++) {
            System.out.printf("P%d\t\t%d\t\t\t\t%d\t\t\t%d\n", i + 1, waitingTimes[i] + turnaroundTimes[i] - waitingTimes[i], waitingTimes[i], turnaroundTimes[i]);
        }

        // Calculate average waiting and turnaround times
        double totalWaitingTime = Arrays.stream(waitingTimes).sum();
        double totalTurnaroundTime = Arrays.stream(turnaroundTimes).sum();

        System.out.printf("\nAverage waiting time = %.2f\n", totalWaitingTime / NUM_PROCESSES);
        System.out.printf("Average turnaround time = %.2f\n", totalTurnaroundTime / NUM_PROCESSES);
    }
}
