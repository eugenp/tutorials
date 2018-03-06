/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package async;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Stockticker implements Runnable {
        public volatile boolean run = true;
        protected AtomicInteger counter = new AtomicInteger(0);
        ArrayList<TickListener> listeners = new ArrayList<TickListener>();
        protected volatile Thread ticker = null;
        protected volatile int ticknr = 0;

        public synchronized void start() {
            run = true;
            ticker = new Thread(this);
            ticker.setName("Ticker Thread");
            ticker.start();
        }

        public synchronized void stop() {
            run = false;
            try {
                ticker.join();
            }catch (InterruptedException x) {
                Thread.interrupted();
            }

            ticker = null;
        }

        public void addTickListener(TickListener listener) {
            if (listeners.add(listener)) {
                if (counter.incrementAndGet()==1) start();
            }

        }

        public void removeTickListener(TickListener listener) {
            if (listeners.remove(listener)) {
                if (counter.decrementAndGet()==0) stop();
            }
        }

        @Override
        public void run() {
            try {

                Stock[] stocks = new Stock[] { new Stock("GOOG", 435.43),
                        new Stock("YHOO", 27.88), new Stock("ASF", 1015.55), };
                Random r = new Random(System.currentTimeMillis());
                while (run) {
                    for (int j = 0; j < 1; j++) {
                        int i = r.nextInt() % 3;
                        if (i < 0)
                            i = i * (-1);
                        Stock stock = stocks[i];
                        double change = r.nextDouble();
                        boolean plus = r.nextBoolean();
                        if (plus) {
                            stock.setValue(stock.getValue() + change);
                        } else {
                            stock.setValue(stock.getValue() - change);
                        }
                        stock.setCnt(++ticknr);
                        for (TickListener l : listeners) {
                            l.tick(stock);
                        }

                    }
                    Thread.sleep(850);
                }
            } catch (InterruptedException ix) {
                // Ignore
            } catch (Exception x) {
                x.printStackTrace();
            }
        }


    public static interface TickListener {
        public void tick(Stock stock);
    }

    public static final class Stock implements Cloneable {
        protected static DecimalFormat df = new DecimalFormat("0.00");
        protected String symbol = "";
        protected double value = 0.0d;
        protected double lastchange = 0.0d;
        protected int cnt = 0;

        public Stock(String symbol, double initvalue) {
            this.symbol = symbol;
            this.value = initvalue;
        }

        public void setCnt(int c) {
            this.cnt = c;
        }

        public int getCnt() {
            return cnt;
        }

        public String getSymbol() {
            return symbol;
        }

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            double old = this.value;
            this.value = value;
            this.lastchange = value - old;
        }

        public String getValueAsString() {
            return df.format(value);
        }

        public double getLastChange() {
            return this.lastchange;
        }

        public void setLastChange(double lastchange) {
            this.lastchange = lastchange;
        }

        public String getLastChangeAsString() {
            return df.format(lastchange);
        }

        @Override
        public int hashCode() {
            return symbol.hashCode();
        }

        @Override
        public boolean equals(Object other) {
            if (other instanceof Stock) {
                return this.symbol.equals(((Stock) other).symbol);
            }

            return false;
        }

        @Override
        public String toString() {
            StringBuilder buf = new StringBuilder("STOCK#");
            buf.append(getSymbol());
            buf.append("#");
            buf.append(getValueAsString());
            buf.append("#");
            buf.append(getLastChangeAsString());
            buf.append("#");
            buf.append(String.valueOf(getCnt()));
            return buf.toString();

        }

        @Override
        public Object clone() {
            Stock s = new Stock(this.getSymbol(), this.getValue());
            s.setLastChange(this.getLastChange());
            s.setCnt(this.cnt);
            return s;
        }
    }
}
