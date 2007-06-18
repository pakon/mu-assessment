package org.apache.jackrabbit.demo.mu.model;

import java.util.List;
import java.util.ArrayList;

/**
 *
 */
public class Question
{
    /**
     *
     */
    private String text;

    /**
     *
     */
    private List<Answer> answers;

    public Question()
    {
        answers = new ArrayList<Answer>();
    }

    public void addAnswer(Answer answer)
    {
        answers.add(answer);
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public List<Answer> getAnswers()
    {
        return answers;
    }

    public void setAnswers(List<Answer> answers)
    {
        this.answers = answers;
    }
}
