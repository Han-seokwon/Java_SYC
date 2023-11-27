package problems;

import users.User;

public class LearningMaterialsHintAdd {
    private User Name;
    private String Material;
    private String Hint;
    private String Key;
    private Problem problem;  // 특정 문제 객체를 저장할 변수

    // 생성자(학습자료 추가용)
    public LearningMaterialsHintAdd(String material, Problem problem) {
        this.Material = material;
        this.problem = problem;
        // 학습자료 추가
//        problem.addProblemReferences(Material);
    }

    // 생성자(힌트 추가용)
    public LearningMaterialsHintAdd(String key, User name, String hint, Problem problem) {
        this.Hint = hint;
        this.Name = name;
        this.Key = key;
        this.problem = problem;
        // 힌트 추가
//        problem.addProblemHint(Key, Name, Hint);
    }

    
}