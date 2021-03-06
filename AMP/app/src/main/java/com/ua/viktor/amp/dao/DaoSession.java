package com.ua.viktor.amp.dao;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import com.ua.viktor.amp.dao.Question;
import com.ua.viktor.amp.dao.Answers;
import com.ua.viktor.amp.dao.Choice;

import com.ua.viktor.amp.dao.QuestionDao;
import com.ua.viktor.amp.dao.AnswersDao;
import com.ua.viktor.amp.dao.ChoiceDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig questionDaoConfig;
    private final DaoConfig answersDaoConfig;
    private final DaoConfig choiceDaoConfig;

    private final QuestionDao questionDao;
    private final AnswersDao answersDao;
    private final ChoiceDao choiceDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        questionDaoConfig = daoConfigMap.get(QuestionDao.class).clone();
        questionDaoConfig.initIdentityScope(type);

        answersDaoConfig = daoConfigMap.get(AnswersDao.class).clone();
        answersDaoConfig.initIdentityScope(type);

        choiceDaoConfig = daoConfigMap.get(ChoiceDao.class).clone();
        choiceDaoConfig.initIdentityScope(type);

        questionDao = new QuestionDao(questionDaoConfig, this);
        answersDao = new AnswersDao(answersDaoConfig, this);
        choiceDao = new ChoiceDao(choiceDaoConfig, this);

        registerDao(Question.class, questionDao);
        registerDao(Answers.class, answersDao);
        registerDao(Choice.class, choiceDao);
    }
    
    public void clear() {
        questionDaoConfig.getIdentityScope().clear();
        answersDaoConfig.getIdentityScope().clear();
        choiceDaoConfig.getIdentityScope().clear();
    }

    public QuestionDao getQuestionDao() {
        return questionDao;
    }

    public AnswersDao getAnswersDao() {
        return answersDao;
    }

    public ChoiceDao getChoiceDao() {
        return choiceDao;
    }

}
