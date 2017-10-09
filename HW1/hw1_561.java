import java.io.*;
import java.util.*;
import java.util.Random;

/**
 * Created by dreamreaver on 2017/9/11.
 */
public class hw1_561 {
    public String readfile()
    {
        int result = 0;
        int length = 0;
        List<String> input_array = new ArrayList<String>();
        //StringBuilder input_array = new StringBuilder("");
        String relativelyPath = System.getProperty("user.dir");
        try{
            File file = new File(relativelyPath + "\\input.txt");
            if(file.isFile() && file.exists())
            {
                //InputStreamReader read = new InputStreamReader(new FileInputStream(file));
                BufferedReader read = new BufferedReader(new FileReader(file));
                LineNumberReader reader = new LineNumberReader(read);
                String line = reader.readLine();
                while (line != null) {
                    if(reader.getLineNumber() == 3)
                        result = Integer.valueOf(line);
                    else if(reader.getLineNumber() == 2)
                        length = Integer.valueOf(line);
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
            System.out.println("IO Exceptions!");
            e.printStackTrace();
        }
        return input_array.toString();
    }
    public void BFS()
    {
        int num = 0;
        int length = 0;
        List<String> input_array = new ArrayList<String>();
        //StringBuilder input_array = new StringBuilder("");
        String relativelyPath = System.getProperty("user.dir");
        try{
            File input = new File(relativelyPath + "\\input.txt");
            if(input.isFile() && input.exists())
            {
                InputStreamReader read = new InputStreamReader(new FileInputStream(input));
                LineNumberReader reader = new LineNumberReader(read);
                String line = reader.readLine();
                while (line != null) {
                    if(reader.getLineNumber() == 3)
                    {
                        num= Integer.valueOf(line);
                    }
                    else if(reader.getLineNumber() == 2)
                    {
                        length = Integer.valueOf(line);
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
        int[][] nums = new int[length][length];
        for(int i = 0; i < length; i ++)
            for(int j = 0; j < length; j ++)
                nums[i][j] = input_array.get(i).charAt(j) - '0';
        LinkedList<node_hw1_561> queue = new LinkedList<node_hw1_561>();
        boolean mark = false;
        node_hw1_561 result = new node_hw1_561();
        node_hw1_561 head = new node_hw1_561();
        head.status = new int[length][length];
        for(int x = 0; x < length; x ++)
            for(int y = 0; y < length; y ++)
                head.status[x][y] = nums[x][y];
        head.val = 0;
        head.self_row = 0;
        head.self_col = 0;
        queue.addLast(head);
        int tree_count = 0;
        for(int i = 0; i < length; i ++)
            for(int j = 0; j < length; j ++)
                if(nums[i][j] == 2)
                    tree_count ++;
        while(!queue.isEmpty())
        {
            node_hw1_561 node = queue.pop();
            if(node.val == num)
            {
                mark = true;
                result.val = node.val;
                result.status = new int[length][length];
                for(int i = 0; i < length; i ++)
                    for(int j = 0; j < length; j ++)
                    {
                        if(node.status[i][j] == 1)
                            result.status[i][j] = 1;
                        if(node.status[i][j] == 2)
                            result.status[i][j] = 2;
                    }
            }
            else
            {
                for(int i = node.self_row; i < node.self_row + length - num + tree_count + 2 && i < length; i ++)
                {
                    for(int j = 0; j < length; j ++)
                    {
                        if(node.status[i][j] == 0)
                        {
                            node_hw1_561 new_node = new node_hw1_561();
                            new_node.val = node.val + 1;
                            new_node.self_row = i;
                            new_node.self_col = j;
                            new_node.status = new int[length][length];
                            for(int x = 0; x < length; x ++)
                                for(int y = 0; y < length; y ++)
                                    new_node.status[x][y] = node.status[x][y];
                            new_node.status[i][j] = 1;
                            for(int r_f = j - 1; r_f >= 0; r_f --)
                                if(new_node.status[i][r_f] == 2)
                                    break;
                                else if(new_node.status[i][r_f] != 1)
                                    new_node.status[i][r_f] = -1;
                            for(int r_b = j + 1; r_b < length; r_b ++)
                                if(new_node.status[i][r_b] == 2)
                                    break;
                                else if(new_node.status[i][r_b] != 1)
                                    new_node.status[i][r_b] = -1;
                            for(int c_u = i - 1; c_u >= 0; c_u --)
                                if(new_node.status[c_u][j] == 2)
                                    break;
                                else if(new_node.status[c_u][j] != 1)
                                    new_node.status[c_u][j] = -1;
                            for(int c_d = i + 1; c_d < length; c_d ++)
                                if(new_node.status[c_d][j] == 2)
                                    break;
                                else if(new_node.status[c_d][j] != 1)
                                    new_node.status[c_d][j] = -1;
                            for(int d_f_u = i - 1; d_f_u >= 0 && d_f_u - (i - j)>= 0; d_f_u --)
                                if(new_node.status[d_f_u][d_f_u - (i - j)] == 2)
                                    break;
                                else if(new_node.status[d_f_u][d_f_u - (i - j)] != 1)
                                    new_node.status[d_f_u][d_f_u - (i - j)] = -1;
                            for(int d_f_d = i + 1; d_f_d < length && (i + j) - d_f_d >= 0; d_f_d ++)
                                if(new_node.status[d_f_d][(i + j) - d_f_d] == 2)
                                    break;
                                else if(new_node.status[d_f_d][(i + j) - d_f_d] != 1)
                                    new_node.status[d_f_d][(i + j) - d_f_d] = -1;
                            for(int d_b_u = i - 1; d_b_u >= 0 && (i + j) - d_b_u < length; d_b_u --)
                                if(new_node.status[d_b_u][(i + j) - d_b_u] == 2)
                                    break;
                                else if(new_node.status[d_b_u][(i + j) - d_b_u] != 1)
                                    new_node.status[d_b_u][(i + j) - d_b_u] = -1;
                            for(int d_b_d = i + 1; d_b_d < length && d_b_d - (i - j) < length; d_b_d ++)
                                if(new_node.status[d_b_d][d_b_d - (i - j)] == 2)
                                    break;
                                else if(new_node.status[d_b_d][d_b_d - (i - j)] != 1)
                                    new_node.status[d_b_d][d_b_d - (i - j)] = -1;
                            queue.addLast(new_node);
                        }
                    }
                }
            }
        }
        try{
            FileOutputStream output = new FileOutputStream(relativelyPath + "\\output.txt");
            if(mark)
            {
                String output_result = "OK\r\n";
                byte[] stringInBytes = output_result.getBytes();
                output.write(stringInBytes);
                for(int i = 0; i < length; i ++)
                {
                    StringBuilder output_status = new StringBuilder("");
                    for(int j = 0; j < length; j ++)
                        output_status.append(result.status[i][j]);
                    output_status.append("\r\n");
                    byte[] contentInBytes = output_status.toString().getBytes();
                    output.write(contentInBytes);
                }
            }
            else
            {
                String output_result = "FAIL\r\n";
                byte[] stringInBytes = output_result.getBytes();
                output.write(stringInBytes);
            }
            output.flush();
            output.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void DFS()
    {
        int num = 0;
        int length = 0;
        StringBuilder input_array = new StringBuilder("");
        String relativelyPath = System.getProperty("user.dir");
        try{
            File input = new File(relativelyPath + "\\input.txt");
            if(input.isFile() && input.exists())
            {
                InputStreamReader read = new InputStreamReader(new FileInputStream(input));
                LineNumberReader reader = new LineNumberReader(read);
                String line = reader.readLine();
                while (line != null) {
                    if(reader.getLineNumber() == 3)
                    {
                        num = Integer.valueOf(line);
                    }
                    else if(reader.getLineNumber() == 2)
                    {
                        length = Integer.valueOf(line);
                    }
                    else if(reader.getLineNumber() > 3)
                        input_array.append(line);
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
        int[][] nums = new int[length][length];
        for(int i = 0; i < length; i ++)
            for(int j = 0; j < length; j ++)
                nums[i][j] = input_array.toString().charAt(i * length + j) - '0';
        LinkedList<node_hw1_561> queue = new LinkedList<node_hw1_561>();
        boolean mark = false;
        node_hw1_561 result = new node_hw1_561();
        node_hw1_561 head = new node_hw1_561();
        head.status = new int[length][length];
        for(int x = 0; x < length; x ++)
            for(int y = 0; y < length; y ++)
                head.status[x][y] = nums[x][y];
        head.val = 0;
        head.self_row = 0;
        head.self_col = 0;
        queue.addLast(head);
        int tree_count = 0;
        for(int i = 0; i < length; i ++)
            for(int j = 0; j < length; j ++)
                if(nums[i][j] == 2)
                    tree_count ++;
        while(!queue.isEmpty())
        {
            node_hw1_561 node = queue.pop();
            if(node.val == num)
            {
                mark = true;
                result.val = node.val;
                result.status = new int[length][length];
                for(int i = 0; i < length; i ++)
                    for(int j = 0; j < length; j ++)
                    {
                        if(node.status[i][j] == 1)
                            result.status[i][j] = 1;
                        if(node.status[i][j] == 2)
                            result.status[i][j] = 2;
                    }
            }
            else
            {
                for(int i = node.self_row; i < node.self_row + length - num + tree_count + 2 && i < length; i ++)
                {
                    for(int j = 0; j < length; j ++)
                    {
                        if(node.status[i][j] == 0)
                        {
                            node_hw1_561 new_node = new node_hw1_561();
                            new_node.val = node.val + 1;
                            new_node.self_row = i;
                            new_node.self_col = j;
                            new_node.status = new int[length][length];
                            for(int x = 0; x < length; x ++)
                                for(int y = 0; y < length; y ++)
                                    new_node.status[x][y] = node.status[x][y];
                            new_node.status[i][j] = 1;
                            for(int r_f = j - 1; r_f >= 0; r_f --)
                                if(new_node.status[i][r_f] == 2)
                                    break;
                                else if(new_node.status[i][r_f] != 1)
                                    new_node.status[i][r_f] = -1;
                            for(int r_b = j + 1; r_b < length; r_b ++)
                                if(new_node.status[i][r_b] == 2)
                                    break;
                                else if(new_node.status[i][r_b] != 1)
                                    new_node.status[i][r_b] = -1;
                            for(int c_u = i - 1; c_u >= 0; c_u --)
                                if(new_node.status[c_u][j] == 2)
                                    break;
                                else if(new_node.status[c_u][j] != 1)
                                    new_node.status[c_u][j] = -1;
                            for(int c_d = i + 1; c_d < length; c_d ++)
                                if(new_node.status[c_d][j] == 2)
                                    break;
                                else if(new_node.status[c_d][j] != 1)
                                    new_node.status[c_d][j] = -1;
                            for(int d_f_u = i - 1; d_f_u >= 0 && d_f_u - (i - j)>= 0; d_f_u --)
                                if(new_node.status[d_f_u][d_f_u - (i - j)] == 2)
                                    break;
                                else if(new_node.status[d_f_u][d_f_u - (i - j)] != 1)
                                    new_node.status[d_f_u][d_f_u - (i - j)] = -1;
                            for(int d_f_d = i + 1; d_f_d < length && (i + j) - d_f_d >= 0; d_f_d ++)
                                if(new_node.status[d_f_d][(i + j) - d_f_d] == 2)
                                    break;
                                else if(new_node.status[d_f_d][(i + j) - d_f_d] != 1)
                                    new_node.status[d_f_d][(i + j) - d_f_d] = -1;
                            for(int d_b_u = i - 1; d_b_u >= 0 && (i + j) - d_b_u < length; d_b_u --)
                                if(new_node.status[d_b_u][(i + j) - d_b_u] == 2)
                                    break;
                                else if(new_node.status[d_b_u][(i + j) - d_b_u] != 1)
                                    new_node.status[d_b_u][(i + j) - d_b_u] = -1;
                            for(int d_b_d = i + 1; d_b_d < length && d_b_d - (i - j) < length; d_b_d ++)
                                if(new_node.status[d_b_d][d_b_d - (i - j)] == 2)
                                    break;
                                else if(new_node.status[d_b_d][d_b_d - (i - j)] != 1)
                                    new_node.status[d_b_d][d_b_d - (i - j)] = -1;
                            queue.addFirst(new_node);
                        }
                    }
                }
            }
        }
        try{
            FileOutputStream output = new FileOutputStream(relativelyPath + "\\output.txt");
            if(mark)
            {
                String output_result = "OK\r\n";
                byte[] stringInBytes = output_result.getBytes();
                output.write(stringInBytes);
                for(int i = 0; i < length; i ++)
                {
                    StringBuilder output_status = new StringBuilder("");
                    for(int j = 0; j < length; j ++)
                        output_status.append(result.status[i][j]);
                    output_status.append("\r\n");
                    byte[] contentInBytes = output_status.toString().getBytes();
                    output.write(contentInBytes);
                }
            }
            else
            {
                String output_result = "FAIL\r\n";
                byte[] stringInBytes = output_result.getBytes();
                output.write(stringInBytes);
            }
            output.flush();
            output.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void SA() {
        int num = 0;
        int length = 0;
        StringBuilder input_array = new StringBuilder("");
        String relativelyPath = System.getProperty("user.dir");
        try{
            File input = new File(relativelyPath + "\\input.txt");
            if(input.isFile() && input.exists())
            {
                InputStreamReader read = new InputStreamReader(new FileInputStream(input));
                LineNumberReader reader = new LineNumberReader(read);
                String line = reader.readLine();
                while (line != null) {
                    if(reader.getLineNumber() == 3)
                        num = Integer.valueOf(line);
                    else if(reader.getLineNumber() == 2)
                        length = Integer.valueOf(line);
                    else if(reader.getLineNumber() > 3)
                        input_array.append(line);
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
        int[][] nums = new int[length][length];
        for(int i = 0; i < length; i ++)
            for(int j = 0; j < length; j ++)
                nums[i][j] = input_array.toString().charAt(i * length + j) - '0';
        int[] record = new int[num + num];
        //random current
        sa_node current = new sa_node();
        current.status = new int[length][length];
        for(int x = 0; x < length; x ++)
            for(int y = 0; y < length; y ++)
                current.status[x][y] = nums[x][y];
        Random random_ini = new Random();
        for(int i = 0; i < num; i ++)
        {
            int ran_row = i;
            int ran_col = random_ini.nextInt(length - 1);
            int ran_count = 0;
            while(current.status[ran_row % num][ran_col] == 1 || current.status[ran_row % num][ran_col] == 2)
            {
                ran_count ++;
                ran_col = random_ini.nextInt(length - 1);
                if(ran_count > num * 2)
                    ran_row ++;
            }
            current.status[ran_row % num][ran_col] = 1;
            record[2 * i] = ran_row % num;
            record[2 * i + 1] = ran_col;
        }
        current.conflict = cal_conflict(current.status, record);
        sa_node best = new sa_node();
        best.conflict = current.conflict;
        best.status = new int[length][length];
        for(int x = 0; x < length; x ++)
            for(int y = 0; y < length; y ++)
                best.status[x][y] = current.status[x][y];
        double t = 1;
        while (t > 0) {
            double T = Integer.MAX_VALUE;
            //generate T
            T = 10000 - t / 100 * t / 100;
            t ++;
            if (T == 0) {
                try {
                    FileOutputStream output = new FileOutputStream(relativelyPath + "\\output666.txt");
                    if (best.conflict == 0) {
                        String output_result = "OK\r\n";
                        byte[] stringInBytes = output_result.getBytes();
                        output.write(stringInBytes);
                        for (int i = 0; i < length; i++) {
                            StringBuilder output_status = new StringBuilder("");
                            for (int j = 0; j < length; j++)
                                output_status.append(best.status[i][j]);
                            output_status.append("\r\n");
                            byte[] contentInBytes = output_status.toString().getBytes();
                            output.write(contentInBytes);
                        }
                    } else {
                        String output_result = "FAIL\r\n";
                        byte[] stringInBytes = output_result.getBytes();
                        output.write(stringInBytes);
                    }
                    output.flush();
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }
            sa_node next = new sa_node();
            next.status = new int[length][length];
            for(int x = 0; x < length; x ++)
                for(int y = 0; y < length; y ++)
                    next.status[x][y] = current.status[x][y];
            Random liz_change_gen = new Random();
            int liz_num = liz_change_gen.nextInt(length - 1);
            int direction = liz_change_gen.nextInt(3);//0: up, 1: left, 2: down, 3: right
            int pos_row = liz_change_gen.nextInt(length - 1);
            int pos_col = liz_change_gen.nextInt(length - 1);

            //int[] change_record = random_move(num, next, record, liz_num, direction);
            int[] change_record = random_move1(next, pos_row, pos_col);
            while(change_record[0] == -1)
            {
                pos_row = liz_change_gen.nextInt(length - 1);
                pos_col = liz_change_gen.nextInt(length - 1);
                change_record = random_move1(next, pos_row, pos_col);
            }
            int[] temp_record = new int[record.length];
            
            for(int i = 0; i < record.length; i ++)
            {
                if(i == liz_num * 2)
                {
                    temp_record[i] = change_record[0];
                }
                else if(i == liz_num * 2 + 1)
                {
                    temp_record[i] = change_record[1];
                }
                else
                    temp_record[i] = record[i];
            }
            next.conflict = cal_conflict(next.status, temp_record);
            double E = current.conflict - next.conflict;
            if (E < 0)
            {
                current = next;
                record = temp_record;
            }
            else {
                double probability = Math.pow(Math.E, (-E / T));
                double random = Math.random();
                if (probability >= random)
                {
                    current = next;
                    record = temp_record;
                }
            }
            if (current.conflict < best.conflict)
                best = current;
            if (best.conflict == 0) {
                try {
                    FileOutputStream output = new FileOutputStream(relativelyPath + "\\output666.txt");
                    String output_result = "OK\r\n";
                    byte[] stringInBytes = output_result.getBytes();
                    output.write(stringInBytes);
                    for (int i = 0; i < length; i++) {
                        StringBuilder output_status = new StringBuilder("");
                        for (int j = 0; j < length; j++)
                            output_status.append(best.status[i][j]);
                        output_status.append("\r\n");
                        byte[] contentInBytes = output_status.toString().getBytes();
                        output.write(contentInBytes);
                    }
                    output.flush();
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }
        }
    }

    public int[] random_move1(sa_node next, int pos_row, int pos_col) {
        int[] result = new int[2];
        if(next.status[pos_row][pos_col] == 1 || next.status[pos_row][pos_col] == 2)
        {
            result[0] = -1;
        }
        else
        {
            result[0] = pos_row;
            result[1] = pos_col;
        }
        return result;
    }

    public int[] random_move(int num, sa_node next, int[] record, int liz_num, int direction) {
        int[] result = new int[2];
        switch (direction) {
            case 0: {
                if (record[liz_num * 2] - 1 >= 0 && next.status[record[liz_num * 2] - 1][record[liz_num * 2 + 1]] != 1 && next.status[record[liz_num * 2] - 1][record[liz_num * 2 + 1]] != 2) {
                    move(next, record, liz_num, 0);
                    result[0] = liz_num;
                    result[1] = 0;
                } else if (record[liz_num * 2] + 1 < next.status.length && next.status[record[liz_num * 2] + 1][record[liz_num * 2 + 1]] != 1 && next.status[record[liz_num * 2] + 1][record[liz_num * 2 + 1]] != 2) {
                    move(next, record, liz_num, 2);
                    result[0] = liz_num;
                    result[1] = 2;
                } else if (record[liz_num * 2 + 1] - 1 >= 0 && next.status[record[liz_num * 2]][record[liz_num * 2 + 1] - 1] != 1 && next.status[record[liz_num * 2]][record[liz_num * 2 + 1] - 1] != 2) {
                    move(next, record, liz_num, 1);
                    result[0] = liz_num;
                    result[1] = 1;
                } else if (record[liz_num * 2 + 1] + 1 < next.status.length && next.status[record[liz_num * 2]][record[liz_num * 2 + 1] + 1] != 1 && next.status[record[liz_num * 2]][record[liz_num * 2 + 1] + 1] != 2) {
                    move(next, record, liz_num, 3);
                    result[0] = liz_num;
                    result[1] = 3;
                } else
                    result[0] = -1;
                break;
            }
            case 1: {
                if (record[liz_num * 2 + 1] - 1 >= 0 && next.status[record[liz_num * 2]][record[liz_num * 2 + 1] - 1] != 1 && next.status[record[liz_num * 2]][record[liz_num * 2 + 1] - 1] != 2) {
                    move(next, record, liz_num, 1);
                    result[0] = liz_num;
                    result[1] = 1;
                } else if (record[liz_num * 2 + 1] + 1 < next.status.length && next.status[record[liz_num * 2]][record[liz_num * 2 + 1] + 1] != 1 && next.status[record[liz_num * 2]][record[liz_num * 2 + 1] + 1] != 2) {
                    move(next, record, liz_num, 3);
                    result[0] = liz_num;
                    result[1] = 3;
                } else if (record[liz_num * 2] - 1 >= 0 && next.status[record[liz_num * 2] - 1][record[liz_num * 2 + 1]] != 1 && next.status[record[liz_num * 2] - 1][record[liz_num * 2 + 1]] != 2) {
                    move(next, record, liz_num, 0);
                    result[0] = liz_num;
                    result[1] = 0;
                } else if (record[liz_num * 2] + 1 < next.status.length && next.status[record[liz_num * 2] + 1][record[liz_num * 2 + 1]] != 1 && next.status[record[liz_num * 2] + 1][record[liz_num * 2 + 1]] != 2) {
                    move(next, record, liz_num, 2);
                    result[0] = liz_num;
                    result[1] = 2;
                } else
                    result[0] = -1;
                break;
            }
            case 2: {
                if (record[liz_num * 2] + 1 < next.status.length && next.status[record[liz_num * 2] + 1][record[liz_num * 2 + 1]] != 1 && next.status[record[liz_num * 2] + 1][record[liz_num * 2 + 1]] != 2) {
                    move(next, record, liz_num, 2);
                    result[0] = liz_num;
                    result[1] = 2;
                } else if (record[liz_num * 2] - 1 >= 0 && next.status[record[liz_num * 2] - 1][record[liz_num * 2 + 1]] != 1 && next.status[record[liz_num * 2] - 1][record[liz_num * 2 + 1]] != 2) {
                    move(next, record, liz_num, 0);
                    result[0] = liz_num;
                    result[1] = 0;
                } else if (record[liz_num * 2 + 1] - 1 >= 0 && next.status[record[liz_num * 2]][record[liz_num * 2 + 1] - 1] != 1 && next.status[record[liz_num * 2]][record[liz_num * 2 + 1] - 1] != 2) {
                    move(next, record, liz_num, 1);
                    result[0] = liz_num;
                    result[1] = 1;
                } else if (record[liz_num * 2 + 1] + 1 < next.status.length && next.status[record[liz_num * 2]][record[liz_num * 2 + 1] + 1] != 1 && next.status[record[liz_num * 2]][record[liz_num * 2 + 1] + 1] != 2) {
                    move(next, record, liz_num, 3);
                    result[0] = liz_num;
                    result[1] = 3;
                } else
                    result[0] = -1;
                break;
            }
            case 3: {
                if (record[liz_num * 2 + 1] + 1 < next.status.length && next.status[record[liz_num * 2]][record[liz_num * 2 + 1] + 1] != 1 && next.status[record[liz_num * 2]][record[liz_num * 2 + 1] + 1] != 2) {
                    move(next, record, liz_num, 3);
                    result[0] = liz_num;
                    result[1] = 3;
                } else if (record[liz_num * 2 + 1] - 1 >= 0 && next.status[record[liz_num * 2]][record[liz_num * 2 + 1] - 1] != 1 && next.status[record[liz_num * 2]][record[liz_num * 2 + 1] - 1] != 2) {
                    move(next, record, liz_num, 1);
                    result[0] = liz_num;
                    result[1] = 1;
                } else if (record[liz_num * 2] - 1 >= 0 && next.status[record[liz_num * 2] - 1][record[liz_num * 2 + 1]] != 1 && next.status[record[liz_num * 2] - 1][record[liz_num * 2 + 1]] != 2) {
                    move(next, record, liz_num, 0);
                    result[0] = liz_num;
                    result[1] = 0;
                } else if (record[liz_num * 2] + 1 < next.status.length && next.status[record[liz_num * 2] + 1][record[liz_num * 2 + 1]] != 1 && next.status[record[liz_num * 2] + 1][record[liz_num * 2 + 1]] != 2) {
                    move(next, record, liz_num, 2);
                    result[0] = liz_num;
                    result[1] = 2;
                } else
                    result[0] = -1;
                break;
            }
        }
        return result;
    }

    public void move(sa_node next, int[] record, int liz_num, int direction)
    {
        next.status[record[liz_num * 2]][record[liz_num * 2 + 1]] = 0;
        switch (direction) {
            case 0: {
                next.status[record[liz_num * 2] - 1][record[liz_num * 2 + 1]] = 1;
                break;
            }
            case 1: {
                next.status[record[liz_num * 2]][record[liz_num * 2 + 1] - 1] = 1;
                break;
            }
            case 2: {
                next.status[record[liz_num * 2] + 1][record[liz_num * 2 + 1]] = 1;
                break;
            }
            case 3: {
                next.status[record[liz_num * 2]][record[liz_num * 2 + 1] + 1] = 1;
                break;
            }
        }
    }

    public int cal_conflict(int[][] status, int[] record)
    {
        int conflict = 0;
        for(int i = 0; i < record.length / 2; i ++)
        {
            for(int r_f = record[2 * i + 1] - 1; r_f >= 0; r_f --){
                if (status[record[2 * i]][r_f] == 2)
                    break;
                else if (status[record[2 * i]][r_f] == 1)
                    conflict++;
            }
            for(int r_b = record[2 * i + 1] + 1; r_b < status.length; r_b ++)
            {

                if(status[record[2 * i]][r_b] == 2)
                    break;
                else if(status[record[2 * i]][r_b] == 1)
                    conflict ++;
            }

            for(int c_u = record[2 * i] - 1; c_u >= 0; c_u --)
                if(status[c_u][record[2 * i + 1]] == 2)
                    break;
                else if(status[c_u][record[2 * i + 1]] == 1)
                    conflict ++;
            for(int c_d = record[2 * i] + 1; c_d < status.length; c_d ++)
                if(status[c_d][record[2 * i + 1]] == 2)
                    break;
                else if(status[c_d][record[2 * i + 1]] == 1)
                    conflict ++;
            for(int d_f_u = record[2 * i] - 1; d_f_u >= 0 && d_f_u - (record[2 * i] - record[2 * i + 1])>= 0; d_f_u --)
                if(status[d_f_u][d_f_u - (record[2 * i] - record[2 * i + 1])] == 2)
                    break;
                else if(status[d_f_u][d_f_u - (record[2 * i] - record[2 * i + 1])] == 1)
                    conflict ++;
            for(int d_f_d = record[2 * i] + 1; d_f_d < status.length && (record[2 * i] + record[2 * i + 1]) - d_f_d >= 0; d_f_d ++)
                if(status[d_f_d][(record[2 * i] + record[2 * i + 1]) - d_f_d] == 2)
                    break;
                else if(status[d_f_d][(record[2 * i] + record[2 * i + 1]) - d_f_d] == 1)
                    conflict ++;
            for(int d_b_u = record[2 * i] - 1; d_b_u >= 0 && (record[2 * i] + record[2 * i + 1]) - d_b_u < status.length; d_b_u --)
                if(status[d_b_u][(record[2 * i] + record[2 * i + 1]) - d_b_u] == 2)
                    break;
                else if(status[d_b_u][(record[2 * i] + record[2 * i + 1]) - d_b_u] == 1)
                    conflict ++;
            for(int d_b_d = record[2 * i] + 1; d_b_d < status.length && d_b_d - (record[2 * i] - record[2 * i + 1]) < status.length; d_b_d ++)
                if(status[d_b_d][d_b_d - (record[2 * i] - record[2 * i + 1])] == 2)
                    break;
                else if(status[d_b_d][d_b_d - (record[2 * i] - record[2 * i + 1])] == 1)
                    conflict ++;
        }
        return (conflict / 2);
    }
}
