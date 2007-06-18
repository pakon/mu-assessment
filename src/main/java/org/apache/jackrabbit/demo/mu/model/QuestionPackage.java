package org.apache.jackrabbit.demo.mu.model;

import java.util.List;
import java.util.ArrayList;

/**
 * 
 */
public class QuestionPackage
{
    /**
     *
     */
    private String author;

    /**
     *
     */
    private List<Question> questions;

    public QuestionPackage()
    {
        questions = new ArrayList<Question>();
    }

    public void addQuestion(Question question)
    {
        questions.add(question);
    }

    public String getAuthor()
    {
        return author;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public List<Question> getQuestions()
    {
        return questions;
    }

    public void setQuestions(List<Question> questions)
    {
        this.questions = questions;
    }
}
