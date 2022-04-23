import java.util.ArrayList;
import java.util.List;

public class Kk {

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
            return size;
        }

        void heapify(int position){

            if(isLeaf(position)){
                return;
            }

            if(heap.get(position) < heap.get(leftChild(position))
              || heap.get(position) < heap.get(rightChild(position))){

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

            heap.set(size, element);

            int curr = size;

            while(heap.get(curr) > heap.get(parent(curr))){
                swap(curr, parent(curr));
                curr = parent(curr);
            }

            size++;
        }

        Long extract(){
            Long element =  heap.get(0);
            heap.set(0, heap.get(size--));
            heapify(0);
            return element;
        }
    }
}