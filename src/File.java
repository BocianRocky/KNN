public enum File {
    IRIS("iris_training.txt","iris_test.txt"),CANCER("cancer_training.txt","cancer_test.txt");

    private final String file_name;
    private final String secfile_name;

    File(String name, String secname) {
        file_name=name;
        secfile_name=secname;
    }

    public String getFile_name() {
        return file_name;
    }

    public String getSecfile_name() {
        return secfile_name;
    }
}
