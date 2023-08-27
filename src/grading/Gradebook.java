package grading;

import java.util.ArrayList;

public class Gradebook {
    private ArrayList<Student> students = new ArrayList<>();

    public void addStudent(Student student) {
        students.add(student);
    }

    public int getMedianScore() {
        // sort list
        quickSort(0, students.size() - 1);

        if (students.size() % 2 == 1) {
            return students.get(students.size() / 2).getScore();
        }

        int medianFirstIdx = students.size() / 2 - 1;
        int medianFirst = students.get(medianFirstIdx).getScore();
        int medianSecond = students.get(medianFirstIdx + 1).getScore();
        return (medianFirst + medianSecond) / 2;
    }

    public int getMedianScoreThreeWay() {
        // sort list
        threeWayQuickSort(0, students.size() - 1);

        if (students.size() % 2 == 1) {
            return students.get(students.size() / 2).getScore();
        }

        int medianFirstIdx = students.size() / 2 - 1;
        int medianFirst = students.get(medianFirstIdx).getScore();
        int medianSecond = students.get(medianFirstIdx + 1).getScore();
        return (medianFirst + medianSecond) / 2;
    }

    /**
     * Sorts the students by score
     */
    private void quickSort(int start, int end) {
        if (start >= end)
            return;

        int pivotScore = students.get(start).getScore();
        int lt = start;
        int gt = end;

        while (lt <= gt) {
            if (students.get(lt).getScore() <= pivotScore)
                lt++;
            else
                swap(lt, gt--);
        }

        swap(start, gt);
        quickSort(start, gt - 1);
        quickSort(gt + 1, end);
    }

    private void threeWayQuickSort(int start, int end) {
        if (start >= end)
            return;

        int lt = start;
        int i = lt;
        int gt = end;
        int pivotValue = students.get(lt).getScore();

        while (i <= gt) {
            if (students.get(i).getScore() < pivotValue)
                swap(lt++, i++);
            else if (students.get(i).getScore() > pivotValue)
                swap(i, gt--);
            else
                i++;
        }

        threeWayQuickSort(start, lt - 1);
        threeWayQuickSort(gt + 1, end);
    }

    private void swap(int i, int j) {
        Student temp = students.get(j);
        students.set(j, students.get(i));
        students.set(i, temp);
    }

    // Tests and Benchmarks
    public static void main(String[] args) {
        int numStudents = 15000;

        Gradebook gb = new Gradebook();
        for (int i = 0; i < numStudents; i++) {
            Student s = new Student(null, null, i, (int) Math.round(Math.random() * 100));
            gb.addStudent(s);
        }
        gb.quickSort(0, gb.students.size() - 1);
        for (int i = 1; i < gb.students.size(); i++) {
            assert gb.students.get(i).getScore() >= gb.students.get(i - 1).getScore() : "Quick sort bad";
        }

        gb = new Gradebook();
        for (int i = 0; i < numStudents; i++) {
            Student s = new Student(null, null, i, (int) Math.round(Math.random() * 100));
            gb.addStudent(s);
        }
        gb.threeWayQuickSort(0, gb.students.size() - 1);
        for (int i = 1; i < gb.students.size(); i++) {
            assert gb.students.get(i).getScore() >= gb.students.get(i - 1).getScore() : "Three way quick sort bad";
        }

        gb = new Gradebook();
        for (int i = 0; i < numStudents; i++) {
            Student s = new Student(null, null, i, (int) Math.round(Math.random() * 100));
            gb.addStudent(s);
        }

        double quickAvg = 0;
        double quickThreeWayAvg = 0;

        for (int i = 0; i < 100; i++) {
            double startTime = System.nanoTime() / Math.pow(10, 6);
            gb.getMedianScore();
            double endTime = System.nanoTime() / Math.pow(10, 6);
            quickAvg += endTime - startTime;

            startTime = System.nanoTime() / Math.pow(10, 6);
            gb.getMedianScoreThreeWay();
            endTime = System.nanoTime() / Math.pow(10, 6);
            quickThreeWayAvg += endTime - startTime;
        }

        System.out.println(quickAvg / 100);
        System.out.println(quickThreeWayAvg / 100);
    }
}
