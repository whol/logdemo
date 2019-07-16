package com.example.logdemo.filter;

import ch.qos.logback.core.boolex.EvaluationException;
import ch.qos.logback.core.boolex.EventEvaluator;
import ch.qos.logback.core.filter.AbstractMatcherFilter;
import ch.qos.logback.core.spi.FilterReply;

public class MyEvaluatorFilter<E> extends AbstractMatcherFilter<E> {

    EventEvaluator<E> evaluator;

    public MyEvaluatorFilter() {
    }

    public void start() {
        if (this.evaluator != null) {
            super.start();
        } else {
            this.addError("No evaluator set for filter " + this.getName());
        }

    }

    public EventEvaluator<E> getEvaluator() {
        return this.evaluator;
    }

    public void setEvaluator(EventEvaluator<E> evaluator) {
        this.evaluator = evaluator;
    }

    public FilterReply decide(E event) {
        if (this.isStarted()) {
            try {
                return this.evaluator.evaluate(event) ? this.onMatch : this.onMismatch;
            } catch (EvaluationException var3) {
                this.addError("Evaluator " + this.evaluator.getName() + " threw an exception", var3);
                return FilterReply.NEUTRAL;
            }
        } else {
            return FilterReply.NEUTRAL;
        }
    }
}
