package problems;

import users.User;

public class LearningMaterialsHintAdd {
    private User Name;
    private String Material;
    private String Hint;
    private String Key;
    private Problem problem;  // Ư�� ���� ��ü�� ������ ����

    // ������(�н��ڷ� �߰���)
    public LearningMaterialsHintAdd(String material, Problem problem) {
        this.Material = material;
        this.problem = problem;
        // �н��ڷ� �߰�
//        problem.addProblemReferences(Material);
    }

    // ������(��Ʈ �߰���)
    public LearningMaterialsHintAdd(String key, User name, String hint, Problem problem) {
        this.Hint = hint;
        this.Name = name;
        this.Key = key;
        this.problem = problem;
        // ��Ʈ �߰�
//        problem.addProblemHint(Key, Name, Hint);
    }

    
}