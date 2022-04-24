import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class Partition {

    Long karmarkarKarp(long [] elements){

        MaxHeap heap = new MaxHeap();

        for(int i = 0; i < elements.length; i++){
            heap.insert(elements[i]);
        }

        while(heap.size > 1){
            Long max1 = heap.extract();
            Long max2 = heap.extract();
            heap.insert(max1 - max2);
        }

        return heap.extract();
    }

    void copyElements(long [] source, long [] destination, int size){
        for(int i = 0; i < size; i++){
            destination[i] = source[i];
        }
    }

    long calculateResidue(long [] elements, long [] sequence, int size){

        long residue = 0L;

        for(int i = 0; i < size; i++){
            residue += (elements[i] * sequence[i]);
        }

        return Math.abs(residue);
    }

    long [] randomNeighbor(long [] neighbor, int size){

        int index1, index2;

        do {
            index1 = new Random().nextInt(size);
            index2 = new Random().nextInt(size);
        } while(index1 == index2);

        neighbor[index1] *= -1;

        if(new Random().nextInt(2) % 2 == 0){
            neighbor[index2] *= -1;
        }

        return neighbor;
    }

    long [] generateStandardRandomSequence(long [] sequence, int size){
        for(int i = 0; i < size; i++){
            sequence[i] = (new Random().nextInt(2) % 2 == 0 ? 1L : -1L);
        }
        return sequence;
    }

    long hillClimbingStandardRandom(int maxIteration, long [] elements,
                                    int size){

        long [] randomSequence = new long [size];
        randomSequence = generateStandardRandomSequence(randomSequence, size);
        long randomResidue = calculateResidue(elements, randomSequence, size);
        long residue;

        for(int i = 0; i < maxIteration; i++){

            long [] neighbor = randomNeighbor(randomSequence, size);
            residue = calculateResidue(elements, neighbor, size);

            if(residue < randomResidue){
                 randomResidue = residue;
                 copyElements(neighbor, randomSequence, size);
            }
        }

        return randomResidue;
    }

    long repeatedRandomStandardRandom(int maxIteration,
                                      long [] sequence, long [] elements,
                                      int size) {
        long [] newSequence = new long [size];
        long residue = 0L;

        for (int i = 0; i < maxIteration; i++) {
            generateStandardRandomSequence(newSequence, size);
            residue = calculateResidue(elements, sequence, size);

            if (calculateResidue(elements, newSequence, size) < residue) {
                residue = calculateResidue(elements, newSequence, size);
                copyElements(newSequence, sequence, size);
            }
        }

        return residue;
    }

    double T(int iteration){
        return Math.pow(10, 10) * Math.pow(0.8, Math.floor(iteration / 300.0));
    }

    long simulatedAnnealingStandardRandom(int maxIteration, long [] elements,
                                          int size, long [] annealingSequence){
        long [] sequence = new long[size];
        long [] randomSolution = generateStandardRandomSequence(sequence, size);
        long randomResidue = calculateResidue(elements, randomSolution, size);
        long [] solution = randomSolution;
        long residue = randomResidue;

        for(int i = 0; i < maxIteration; i++){
            long [] potentialSolution = randomNeighbor(sequence, size);
            long potentialResidue = calculateResidue(elements, potentialSolution, size);

            double probability = Math.exp(-(potentialResidue - randomResidue)/ T(i));

            if(potentialResidue < randomResidue || new Random().nextDouble() < probability) {
                copyElements(potentialSolution, randomSolution, size);
                randomResidue = potentialResidue;
            }

            if(randomResidue < residue){
                residue = randomResidue;
                copyElements(randomSolution, solution, size);
            }
        }

        return residue;
    }

    void processPartition(long [] partition,
                          long [] elements, long [] destination,
                          int size){

        for(int i = 0; i < size; i++){
            int index = Long.valueOf(partition[i]).intValue();
            Long value = destination[index] + elements[i];
            destination[index] = value;
        }
    }

    void generateRandomPartition(long [] partition, int size, Long bound){
        for(int i = 0; i < size; i++){
            partition[i] = (long) Math.ceil(new Random().nextDouble() * bound);
        }
    }

    void processPartitionNeighbor(long [] partition,
                                  long [] newPartition,
                                  int size){
        copyElements(partition, newPartition, size);

        int index1;
        long index2;

        do {
            index1 = new Random().nextInt(size);
            index2 = Long.valueOf(new Random().nextInt(size));
        } while(newPartition[index1] == index2);

        newPartition[index1] = index2;
    }

    Long partitionedHillClimbing(int maxIteration,
                                 long [] partition, long [] elements,
                                 int size){
        Long residue = 0L;
        long [] partitioner = new long[size];
        long [] newPartition = new long[size];
        long [] newElements = new long[size];

        for(int i = 0; i < maxIteration; i++){
            processPartition(partition, elements, partitioner, size);
            processPartitionNeighbor(partition, newPartition, size);
            processPartition(newPartition, elements, newElements, size);

            Long partitionerKarmarkar = karmarkarKarp(partitioner);
            Long newElementsKarmarkar = karmarkarKarp(newElements);

            if(partitionerKarmarkar > newElementsKarmarkar){
                residue = newElementsKarmarkar;
                copyElements(newPartition, partition, size);
            }
        }

        return residue;
    }

    Long partitionedRepeatedRandom(int maxIteration,
                                   long [] partition, long [] elements,
                                   int size){
        Long residue = 0L;
        long [] newElements = new long[size];
        long [] newPartition = new long[size];
        long [] partitioner = new long[size];

        for(int i = 0; i < maxIteration; i++){
            processPartition(partition, elements, partitioner, size);
            generateRandomPartition(newPartition, size, Long.valueOf(size));
            processPartition(newPartition, elements, newElements, size);

            residue = karmarkarKarp(partitioner);
            Long newElementsResidue = karmarkarKarp(newElements);

            if(residue > newElementsResidue){
                copyElements(newPartition, partition, size);
            }
        }


        return residue;
    }

    Long partitionedSimulatedAnnealing(int maxIteration,
                                       long [] partition, long [] elements,
                                       int size, long [] annealingPartition){

        Long residue = 0L;
        long [] newElements = new long[size];
        long [] newPartition = new long[size];
        long [] partitioner = new long[size];
        long [] newPartitioner = new long[size];

        for(int i = 0; i < maxIteration; i++){
            processPartition(partition, elements, partitioner, size);
            processPartitionNeighbor(partition, newPartition, size);
            processPartition(newPartition, elements, newElements, size);
            processPartition(annealingPartition, elements, newPartitioner, size);

            Long partitionerResidue = karmarkarKarp(partitioner);
            Long newElementsResidue = karmarkarKarp(newElements);
            Long newPartitionerResidue = karmarkarKarp(newPartitioner);

            double probability = Math.exp(-(newElementsResidue - partitionerResidue) / T(i));

            if(partitionerResidue > newElementsResidue
                    || new Random().nextDouble() <= probability){
                copyElements(newPartition, partition, size);
            }

            processPartition(partition, elements, partitioner, size);
            partitionerResidue = karmarkarKarp(partitioner);

            if(partitionerResidue < newPartitionerResidue){
                residue = partitionerResidue;
                copyElements(partition, annealingPartition, size);
            }
        }


        return residue;
    }

    class MaxHeap {

        private long [] heap;
        private int size;
        private int maxSize = 100;

        public MaxHeap(){
            this.size = 0;
            this.heap = new long[maxSize];
        }

        void swap(int aPos, int bPos){
            long temp = heap[aPos];
            heap[aPos] = heap[bPos];
            heap[bPos] = temp;
        }

        boolean isLeaf(int position){
            return (position > (size / 2)) && (position <= size);
        }

        int leftChild(int position){
            return (2 * position) + 1;
        }

        int rightChild(int position){
            return (2 * position) + 2;
        }

        int parent(int position){
            return (position - 1) / 2;
        }

        void heapify(int position){

            if(isLeaf(position)){
                return;
            }

            if((leftChild(position) < size && heap[position] < heap[leftChild(position)])
              || (rightChild(position) < size && heap[position] < heap[rightChild(position)])){

                if(heap[leftChild(position)] >
                        heap[rightChild(position)]){
                    swap(position, leftChild(position));
                    heapify(leftChild(position));
                } else {
                    swap(position, rightChild(position));
                    heapify(rightChild(position));
                }
            }
        }

        void insert(Long element){

            heap[size] = element;

            int curr = size;

            while(heap[curr] > heap[parent(curr)]){
                swap(curr, parent(curr));
                curr = parent(curr);
            }

            size++;
        }

        Long extract(){
            Long element = heap[0];
            heap[0] = heap[--size];
            heapify(0);
            return element;
        }
    }


    public static void main(String [] args){

        if(args.length != 3){
            throw new RuntimeException("Pass: Partition.java <flag> <algorithm> <input_file>");
        }

        String fileName = args[2];
        Integer algorithm = Integer.parseInt(args[1]);
        List<Long> numsList = new ArrayList<>();
        int maxSize = 100;

        try (Stream<String> stream = Files.lines(Paths.get(String.valueOf(new File(fileName))))) {
            stream.forEach((line) -> numsList.add(Long.parseLong(line)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Partition partition = new Partition();
        int maxIteration = 25000;

        long [] numsArray = new long[maxSize];

        for(int i = 0; i < maxSize; i++){
            numsArray[i] = numsList.get(i);
        }

        switch(algorithm){
            case 0: {
                System.out.println(partition.karmarkarKarp(numsArray));
                break;
            }
            case 1: {
                long [] sequence = new long[maxSize];
                partition.generateStandardRandomSequence(sequence, maxSize);
                System.out.println(partition.repeatedRandomStandardRandom(maxIteration, sequence, numsArray, maxSize));
                break;
            }
            case 2: {
                partition.hillClimbingStandardRandom(maxIteration, numsArray, maxSize);
                break;
            }
            case 3: {
                long [] sequence = new long[maxSize];
                partition.generateStandardRandomSequence(sequence, maxSize);
                long [] annealingSequence = new long[maxSize];
                partition.copyElements(sequence, annealingSequence, numsList.size());
                partition.simulatedAnnealingStandardRandom(maxIteration, numsArray, maxSize, annealingSequence);
                break;
            }
            case 11: {
                long [] partitionLst = new long[maxSize];
                partition.generateRandomPartition(partitionLst, maxSize, Long.valueOf(maxSize));
                partition.partitionedRepeatedRandom(maxIteration, partitionLst, numsArray, maxSize);
                break;
            }
            case 12: {
                long [] partitionLst = new long [maxSize];
                partition.generateRandomPartition(partitionLst, maxSize, Long.valueOf(maxSize));
                partition.partitionedHillClimbing(maxIteration, partitionLst, numsArray, maxSize);
                break;
            }
            case 13: {
                long [] partitionLst = new long[maxSize];
                partition.generateRandomPartition(partitionLst, maxSize, Long.valueOf(maxSize));
                long [] annealingPartition = new long[maxSize];
                partition.copyElements(partitionLst, annealingPartition, numsList.size());
                partition.partitionedSimulatedAnnealing(maxIteration, partitionLst, numsArray, maxSize, annealingPartition);
                break;
            }
        }

    }
}