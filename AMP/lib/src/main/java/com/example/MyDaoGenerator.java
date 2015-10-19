package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;

public class MyDaoGenerator {
    public static void main(String args[]) throws Exception {
        Schema schema = new Schema(3, "com.ua.viktor.amp.model");

        Entity question = schema.addEntity("Question");
        question.addIdProperty();
        question.addStringProperty("text").notNull();

        Entity choice = schema.addEntity("Choice");
        choice.setTableName("CHOICES");
        choice.addIdProperty();
        choice.addStringProperty("item");
        Property questionId = choice.addLongProperty("questionId").notNull().getProperty();
        choice.addToOne(question, questionId);

        ToMany questionToChoice = question.addToMany(choice, questionId);
        questionToChoice.setName("question");




        new DaoGenerator().generateAll(schema, "../AMP/app/src/main/java");
    }
}
