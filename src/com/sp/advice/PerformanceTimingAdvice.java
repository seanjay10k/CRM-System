package com.sp.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;


public class PerformanceTimingAdvice {
	
	private static final int NANOSEC_IN_MILLISEC = 1000000;

	public Object RecordTiming(ProceedingJoinPoint jp) throws Throwable {
		//before method is invoked run this 
		double startTimer=System.nanoTime();
		
		try {
		//invoke the method
		Object returnvalue= jp.proceed();
		return returnvalue;
		}
		finally {
			double stopTimer=System.nanoTime();
			double timeTaken= stopTimer-startTimer;
			double timeTakenInMilliseconds= timeTaken/NANOSEC_IN_MILLISEC;
			
			System.out.println("In "+jp.getTarget().getClass().getSimpleName()+" class, "+jp.getSignature().getName()+" took "+timeTakenInMilliseconds+" MilliSeconds. ");
		}
	}

}
