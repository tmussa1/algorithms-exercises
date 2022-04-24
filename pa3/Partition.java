import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class Partition {

    Long karmarkarKarp(List<Long> elements){

        MaxHeap heap = new MaxHeap();

        for(int i = 0; i < elements.size(); i++){
            heap.insert(elements.get(i));
        }

        while(heap.getSize() > 1){
            Long max1 = heap.extract();
            Long max2 = heap.extract();
            heap.insert(max1 - max2);
        }

        return heap.extract();
    }

    void copyElements(List<Long> source, List<Long> destination, int size){
        destination = new ArrayList<>();
        for(int i = 0; i < size; i++){
            destination.add(source.get(i));
        }
    }

    Long calculateResidue(List<Long> elements, List<Long> sequence, int size){

        Long residue = 0L;

        for(int i = 0; i < size; i++){
            residue += (elements.get(i) * sequence.get(i));
        }

        return Math.abs(residue);
    }

    void randomNeighbor(List<Long> source, List<Long> destination, int size){

        copyElements(source, destination, size);

        int index1, index2;

        do {
            index1 = new Random().nextInt(source.size());
            index2 = new Random().nextInt(source.size());
        } while(index1 == index2);

        destination.set(index1, destination.get(index1) * -1);

        if(new Random().nextInt(2) == 0){
            destination.set(index2, destination.get(index2) * -1);
        }
    }

    void generateStandardRandomSequence(List<Long> sequence, int size){
        for(int i = 0; i < size; i++){
            sequence.add(new Random().nextInt(2) % 2 == 0 ? 1L : -1L);
        }
    }

    Long hillClimbingStandardRandom(int maxIteration,
                                    List<Long> sequence, List<Long> elements,
                                    int size){

        List<Long> newSequence = new ArrayList<>();
        Long residue = 0L;

        for(int i = 0; i < maxIteration; i++){

            randomNeighbor(sequence, newSequence, size);
            residue = calculateResidue(elements, sequence, size);

            if(calculateResidue(elements, newSequence, size) < residue){
                copyElements(newSequence, sequence, size);
            }
        }

        return residue;
    }

    Long repeatedRandomStandardRandom(int maxIteration,
                                      List<Long> sequence, List<Long> elements,
                                      int size) {
        List<Long> newSequence = new ArrayList<>();
        Long residue = 0L;

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

    Long simulatedAnnealingStandardRandom(int maxIteration,
                                          List<Long> sequence, List<Long> elements,
                                          int size, List<Long> annealingSequence){
        List<Long> newSequence = new ArrayList<>();
        Long residue = 0L;
        Long annealingResidue = 0L;

        copyElements(sequence, annealingSequence, size);

        for(int i = 0; i < maxIteration; i++){
            randomNeighbor(sequence, newSequence, size);
            double probability = Math.exp(-(calculateResidue(elements, newSequence, size) -
                               calculateResidue(elements, sequence, size))/ T(i));

            if(calculateResidue(elements, newSequence, size) <
               calculateResidue(elements, sequence, size)
               || new Random().nextDouble() < probability) {
                copyElements(newSequence, sequence, size);
            }

            annealingResidue = calculateResidue(elements, annealingSequence, size);

            if(calculateResidue(elements, sequence, size) < annealingResidue) {
                residue = calculateResidue(elements, sequence, size);
                copyElements(sequence, annealingSequence, size);
            }
        }

        return residue;
    }

    void processPartition(List<Long> partition,
                          List<Long> elements, List<Long> destination,
                          int size){

        for(int i = 0; i < size; i++){
            Long value = destination.get(partition.get(i).intValue()) + elements.get(i);
            destination.add(partition.get(i).intValue(), value);
        }
    }

    void generateRandomPartition(List<Long> partition, int size, Long bound){
        for(int i = 0; i < size; i++){
            partition.set(i, (long) Math.ceil(new Random().nextDouble() * bound));
        }
    }

    void processPartitionNeighbor(List<Long> partition,
                                  List<Long> newPartition,
                                  int size){
        copyElements(partition, newPartition, size);

        int index1;
        Long index2;

        do {
            index1 = new Random().nextInt(size);
            index2 = Long.valueOf(new Random().nextInt(size));
        } while(newPartition.get(index1).equals(index2));

        newPartition.set(index1, index2);
    }

    Long partitionedHillClimbing(int maxIteration,
                                 List<Long> partition, List<Long> elements,
                                 int size){
        Long residue = 0L;
        List<Long> partitioner = new ArrayList<>();
        List<Long> newPartition = new ArrayList<>();
        List<Long> newElements = new ArrayList<>();

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
                                   List<Long> partition, List<Long> elements,
                                   int size){
        Long residue = 0L;
        List<Long> newElements = new ArrayList<>();
        List<Long> newPartition = new ArrayList<>();
        List<Long> partitioner = new ArrayList<>();

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
                                       List<Long> partition, List<Long> elements,
                                       int size, List<Long> annealingPartition){

        Long residue = 0L;
        List<Long> newElements = new ArrayList<>();
        List<Long> newPartition = new ArrayList<>();
        List<Long> partitioner = new ArrayList<>();
        List<Long> newPartitioner = new ArrayList<>();

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

        private List<Long> heap;
        private int size;

        public MaxHeap(){
            this.size = 0;
            this.heap = new ArrayList<>();
        }

        void swap(int aPos, int bPos){
            Long temp = heap.get(aPos);
            heap.set(aPos, heap.get(bPos));
            heap.set(bPos, temp);
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

        public int getSize() {
            return heap.size();
        }

        void heapify(int position){

            if(isLeaf(position)){
                return;
            }

            if((position < getSize() &&
                    rightChild(position) < getSize() &&
                    leftChild(position) < getSize()) &&
                    (heap.get(position) < heap.get(leftChild(position))
              || heap.get(position) < heap.get(rightChild(position)))){

                if(heap.get(leftChild(position)) >
                        heap.get(rightChild(position))){
                    swap(position, leftChild(position));
                    heapify(leftChild(position));
                } else {
                    swap(position, rightChild(position));
                    heapify(rightChild(position));
                }
            }
        }

        void insert(Long element){

            heap.add(element);

            int curr = getSize() - 1;

            while(heap.get(curr) > heap.get(parent(curr))){
                swap(curr, parent(curr));
                curr = parent(curr);
            }
        }

        Long extract(){
            Long element =  heap.get(0);
            heap.set(0, heap.get(getSize() - 1));
            heap.remove(getSize() - 1);
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
        List<Long> nums = new ArrayList<>();

        try (Stream<String> stream = Files.lines(Paths.get(String.valueOf(new File(fileName))))) {
            stream.forEach((line) -> nums.add(Long.parseLong(line)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Partition partition = new Partition();
        int maxIteration = 25000;

        switch(algorithm){
            case 0: {
                System.out.println(partition.karmarkarKarp(nums));
                break;
            }
            case 1: {
                List<Long> sequence = new ArrayList<>();
                partition.generateStandardRandomSequence(sequence, nums.size());
                System.out.println(partition.repeatedRandomStandardRandom(maxIteration, sequence, nums, nums.size()));
                break;
            }
            case 2: {
                List<Long> sequence = new ArrayList<>();
                partition.generateStandardRandomSequence(sequence, nums.size());
                partition.hillClimbingStandardRandom(maxIteration, sequence, nums, nums.size());
                break;
            }
            case 3: {
                List<Long> sequence = new ArrayList<>();
                partition.generateStandardRandomSequence(sequence, nums.size());
                List<Long> annealingSequence = new ArrayList<>();
                partition.copyElements(sequence, annealingSequence, nums.size());
                partition.simulatedAnnealingStandardRandom(maxIteration, sequence, nums, nums.size(), annealingSequence);
                break;
            }
            case 11: {
                List<Long> partitionLst = new ArrayList<>();
                partition.generateRandomPartition(partitionLst, nums.size(), Long.valueOf(nums.size()));
                partition.partitionedRepeatedRandom(maxIteration, partitionLst, nums, nums.size());
                break;
            }
            case 12: {
                List<Long> partitionLst = new ArrayList<>();
                partition.generateRandomPartition(partitionLst, nums.size(), Long.valueOf(nums.size()));
                partition.partitionedHillClimbing(maxIteration, partitionLst, nums, nums.size());
                break;
            }
            case 13: {
                List<Long> partitionLst = new ArrayList<>();
                partition.generateRandomPartition(partitionLst, nums.size(), Long.valueOf(nums.size()));
                List<Long> annealingPartition = new ArrayList<>();
                partition.copyElements(partitionLst, annealingPartition, nums.size());
                partition.partitionedSimulatedAnnealing(maxIteration, partitionLst, nums, nums.size(), annealingPartition);
                break;
            }
        }

    }
}