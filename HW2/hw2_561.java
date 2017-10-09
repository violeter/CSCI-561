import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by dreamreaver on 2017/10/4.
 */
public class hw2_561 {
    public void minimax()
    {
        int size = 0;
        int types = 0;
        List<String> input_array = new ArrayList<String>();
        String relativelyPath = System.getProperty("user.dir");
        try{
            File input = new File(relativelyPath + "\\input_1.txt");
            if(input.isFile() && input.exists())
            {
                InputStreamReader read = new InputStreamReader(new FileInputStream(input));
                LineNumberReader reader = new LineNumberReader(read);
                String line = reader.readLine();
                while (line != null) {
                    if(reader.getLineNumber() == 1)
                    {
                        size = Integer.valueOf(line);
                    }
                    else if(reader.getLineNumber() == 2)
                    {
                        types = Integer.valueOf(line);
                    }
                    else if(reader.getLineNumber() > 3)
                        input_array.add(line);
                    line = reader.readLine();
                }
                read.close();
                reader.close();
            }else{
                System.out.println("No such file");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        node_hw2_561 root = new node_hw2_561();
        root.level = 0;
        root.score = 0;
        root.state = new char[size][size];
        for(int i = 0; i < size; i ++)
            for(int j = 0; j < size; j ++)
                root.state[i][j] = input_array.get(i).charAt(j);
        int[] result_array = max_value(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
        System.out.println("No such file");
    }

    public int[] max_value(node_hw2_561 node, int alpha, int beta)
    {
        List<int[]> successors = new ArrayList<int[]>();
        int size = node.state.length;
        int v = 0;
        int[] result_array = new int[3];
        char[][] record_state = new char[size][size];
        for(int i = 0; i < size; i ++)
            for(int j = 0; j < size; j ++)
                record_state[i][j] = node.state[i][j];
        successors.add(state_record(record_state));
        int[] record_cordinate = state_record(record_state);
        while(record_cordinate[0] >= 0)
        {
            successors.add(record_cordinate);
            record_cordinate = state_record(record_state);
        }
        if(successors.size() > 1 && node.level < 3)
        {

            for(int x = 0; x < successors.size(); x ++)
            {
                node_hw2_561 new_node = new node_hw2_561();
                new_node.level = node.level + 1;
                new_node.state = new char[size][size];
                for(int i = 0; i < size; i ++)
                    for(int j = 0; j < size; j ++)
                        new_node.state[i][j] = node.state[i][j];
                state_calculate(new_node, successors.get(x));
                if(node.level % 2 == 0)
                    new_node.score = node.score + new_node.score * new_node.score;
                else
                    new_node.score = node.score - new_node.score * new_node.score;
                regenerate(new_node.state);
                v = min_value(new_node, alpha, beta)[0];
                if(v > alpha)
                {
                    alpha = v;
                    result_array[0] = alpha;
                    result_array[1] = successors.get(x)[0];
                    result_array[2] = successors.get(x)[1];
                }
                if(alpha >= beta)
                {
                    result_array[0] = beta;
                    result_array[1] = successors.get(x)[0];
                    result_array[2] = successors.get(x)[1];
                    return result_array;
                }
            }
            return result_array;
        }
        else
        {
            node_hw2_561 new_node = new node_hw2_561();
            new_node.level = node.level + 1;
            new_node.state = new char[size][size];
            for(int i = 0; i < size; i ++)
                for(int j = 0; j < size; j ++)
                    new_node.state[i][j] = node.state[i][j];
            state_calculate(new_node, successors.get(0));
            if(node.level % 2 == 0)
                new_node.score = node.score + new_node.score * new_node.score;
            else
                new_node.score = node.score - new_node.score * new_node.score;
            result_array[0] = new_node.score;
            result_array[1] = successors.get(0)[0];
            result_array[2] = successors.get(0)[1];
            return result_array;
        }
    }

    public int[] min_value(node_hw2_561 node, int alpha, int beta)
    {
        List<int[]> successors = new ArrayList<int[]>();
        int size = node.state.length;
        int v = 0;
        int[] result_array = new int[3];
        char[][] record_state = new char[size][size];
        for(int i = 0; i < size; i ++)
            for(int j = 0; j < size; j ++)
                record_state[i][j] = node.state[i][j];
        successors.add(state_record(record_state));
        int[] record_cordinate = state_record(record_state);
        while(record_cordinate[0] >= 0)
        {
            successors.add(record_cordinate);
            record_cordinate = state_record(record_state);
        }
        if(successors.size() > 1 && node.level < 3)
        {

            for(int x = 0; x < successors.size(); x ++)
            {
                node_hw2_561 new_node = new node_hw2_561();
                new_node.level = node.level + 1;
                new_node.state = new char[size][size];
                for(int i = 0; i < size; i ++)
                    for(int j = 0; j < size; j ++)
                        new_node.state[i][j] = node.state[i][j];
                state_calculate(new_node, successors.get(x));
                if(node.level % 2 == 0)
                    new_node.score = node.score + new_node.score * new_node.score;
                else
                    new_node.score = node.score - new_node.score * new_node.score;
                regenerate(new_node.state);
                v = max_value(new_node, alpha, beta)[0];
                if(v < beta)
                {
                    beta = v;
                    result_array[0] = beta;
                    result_array[1] = successors.get(x)[0];
                    result_array[2] = successors.get(x)[1];
                }
                if(alpha >= beta)
                {
                    result_array[0] = alpha;
                    result_array[1] = successors.get(x)[0];
                    result_array[2] = successors.get(x)[1];
                    return result_array;
                }
            }
            return result_array;
        }
        else
        {
            node_hw2_561 new_node = new node_hw2_561();
            new_node.level = node.level + 1;
            new_node.state = new char[size][size];
            for(int i = 0; i < size; i ++)
                for(int j = 0; j < size; j ++)
                    new_node.state[i][j] = node.state[i][j];
            state_calculate(new_node, successors.get(0));
            if(node.level % 2 == 0)
                new_node.score = node.score + new_node.score * new_node.score;
            else
                new_node.score = node.score - new_node.score * new_node.score;
            result_array[0] = new_node.score;
            result_array[1] = successors.get(0)[0];
            result_array[2] = successors.get(0)[1];
            return result_array;
        }
    }
    public int[] state_record(char[][] state)
    {
        for(int i = 0; i < state.length; i ++)
        {
            for(int j = 0; j < state[i].length; j ++)
            {
                if(state[i][j] != '*')
                {
                    DFS_record(state, i, j, state[i][j]);
                    int[] result = {i, j};
                    return result;
                }
            }
        }
        int[] result = {-1, -1};
        return result;
    }

    public void DFS_record(char[][] state, int row, int col, char type)
    {
        if (row >= state.length || row < 0 || col >= state.length || col < 0 || state[row][col] != type)
            return;
        else {
            state[row][col] = '*';
            DFS_record(state, row + 1, col, type);
            DFS_record(state, row - 1, col, type);
            DFS_record(state, row, col + 1, type);
            DFS_record(state, row, col - 1, type);
        }
    }

    public void regenerate(char[][] state)
    {
        for(int j = 0; j < state[0].length; j ++)
        {
            int i = state.length - 1;
            while(state[i][j] != '*')
            {
                i --;
                if(i < 0)
                    break;
            }
            if(i < 0)
                continue;
            int bottom = i;
            i --;
            int count = 1;
            while(i >= 0)
            {
                if(state[i][j] != '*')
                {
                    state[bottom][j] = state[i][j];
                    bottom --;
                }
                else
                    count ++;
                i --;
            }
            for(i = 0; i < count; i ++)
                state[i][j] = '*';
        }
    }

    public void state_calculate(node_hw2_561 node, int[] cordinate)
    {
        char[][] state = node.state;
        if(cordinate.length != 2)
            System.out.println("No such file");
        for(int i = cordinate[0]; i < state.length; i ++)
        {

            for(int j = cordinate[1]; j < state[i].length; j ++)
            {
                if(state[i][j] != '*')
                {
                    DFS_calculate(state, i, j, state[i][j], node);
                    return;
                }
            }
        }
    }

    public void DFS_calculate(char[][] state, int row, int col, char type, node_hw2_561 node)
    {
        if (row >= state.length || row < 0 || col >= state.length || col < 0 || state[row][col] != type)
            return;
        else {
            state[row][col] = '*';
            node.score ++;
            DFS_calculate(state, row + 1, col, type, node);
            DFS_calculate(state, row - 1, col, type, node);
            DFS_calculate(state, row, col + 1, type, node);
            DFS_calculate(state, row, col - 1, type, node);
        }
    }
}
