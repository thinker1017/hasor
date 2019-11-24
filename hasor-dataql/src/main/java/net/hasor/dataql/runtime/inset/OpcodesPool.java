/*
 * Copyright 2008-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.hasor.dataql.runtime.inset;
import net.hasor.dataql.ProcessException;
import net.hasor.dataql.runtime.InsetProcess;
import net.hasor.dataql.runtime.InsetProcessContext;
import net.hasor.dataql.runtime.InstSequence;
import net.hasor.dataql.runtime.mem.DataHeap;
import net.hasor.dataql.runtime.mem.DataStack;
import net.hasor.dataql.runtime.mem.EnvStack;

/**
 * 指令池
 * @author 赵永春 (zyc@hasor.net)
 * @version : 2017-07-19
 */
public class OpcodesPool {
    private InsetProcess[] processes = new InsetProcess[255];

    public static OpcodesPool newPool() {
        OpcodesPool pool = new OpcodesPool();
        {
            pool.addInsetProcess(new LDC_B());
            pool.addInsetProcess(new LDC_D());
            pool.addInsetProcess(new LDC_S());
            pool.addInsetProcess(new LDC_N());
            pool.addInsetProcess(new NEW_O());
            pool.addInsetProcess(new NEW_A());
            //
            pool.addInsetProcess(new LOAD());
            pool.addInsetProcess(new STORE());
            pool.addInsetProcess(new GET());
            pool.addInsetProcess(new PUT());
            pool.addInsetProcess(new PULL());
            pool.addInsetProcess(new PUSH());
            //
            pool.addInsetProcess(new RETURN());
            pool.addInsetProcess(new EXIT());
            pool.addInsetProcess(new THROW());
            //
            pool.addInsetProcess(new UO());
            pool.addInsetProcess(new DO());
            //
            pool.addInsetProcess(new IF());
            pool.addInsetProcess(new GOTO());
            pool.addInsetProcess(new OPT());
            pool.addInsetProcess(new POP());
            pool.addInsetProcess(new LOAD_C());
            pool.addInsetProcess(new E_PUSH());
            pool.addInsetProcess(new E_POP());
            pool.addInsetProcess(new E_LOAD());
            pool.addInsetProcess(new CAST_I());
            //
            pool.addInsetProcess(new CALL());
            pool.addInsetProcess(new M_REF());
            pool.addInsetProcess(new M_STAR());
            pool.addInsetProcess(new M_DEF());
            pool.addInsetProcess(new LOCAL());
            //
            pool.addInsetProcess(new LABEL());
            pool.addInsetProcess(new LINE());
        }
        return pool;
    }

    private void addInsetProcess(InsetProcess inst) {
        this.processes[inst.getOpcode()] = inst;
    }

    public void doWork(InstSequence sequence, DataHeap dataHeap, DataStack dataStack, EnvStack envStack, InsetProcessContext context) throws ProcessException {
        InsetProcess process = this.processes[sequence.currentInst().getInstCode()];
        if (process == null) {
            return;
        }
        process.doWork(sequence, dataHeap, dataStack, envStack, context);
    }
}
