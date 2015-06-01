/*
 * Copyright (c) www.ultrapower.com.cn
 */

package persistence.persis;

/**
 * Created by liekkas on 15/5/6.
 * Processor receives an instance of Command. Event is generated from this and persisted. On successful
 * persist, it updates the state of the processor. This allows a complete recovery of state in case of failure
 * by replaying the events that are in the journal and snapshot(s).
 */
public class BaseProcessor{
}
