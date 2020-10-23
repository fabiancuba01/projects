public static int maxaRow(int[][] array) {
        int bigRow = -100000;
        int somem = -1;
        for (int i = 0; i < array.length; i++) {
            int w = 0;
            for (int j = 0; j < array[i].length; j++) {
                w += array[i][j];
            }
            if (w >= bigRow) {
                bigRow = w;
                somem = i;
            }
        }
        return somem;
    }