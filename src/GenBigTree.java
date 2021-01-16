import java.util.Random;

public class GenBigTree
{

    public GenBigTree()
    {
        field_881_b = new Random();
        field_878_e = 0;
        field_876_g = 0.61799999999999999D;
        field_875_h = 1.0D;
        field_874_i = 0.38100000000000001D;
        field_873_j = 1.0D;
        field_872_k = 1.0D;
        field_871_l = 1;
        field_870_m = 12;
        field_869_n = 4;
    }

    void func_521_a()
    {
        field_877_f = (int)((double)field_878_e * field_876_g);
        if(field_877_f >= field_878_e)
        {
            field_877_f = field_878_e - 1;
        }
        int i = (int)(1.3819999999999999D + Math.pow((field_872_k * (double)field_878_e) / 13D, 2D));
        if(i < 1)
        {
            i = 1;
        }
        int ai[][] = new int[i * field_878_e][4];
        int j = (field_879_d[1] + field_878_e) - field_869_n;
        int k = 1;
        int l = field_879_d[1] + field_877_f;
        int i1 = j - field_879_d[1];
        ai[0][0] = field_879_d[0];
        ai[0][1] = j;
        ai[0][2] = field_879_d[2];
        ai[0][3] = l;
        j--;
        while(i1 >= 0) 
        {
            int j1 = 0;
            float f = func_528_a(i1);
            if(f < 0.0F)
            {
                j--;
                i1--;
            } else
            {
                double d = 0.5D;
                for(; j1 < i; j1++)
                {
                    double d1 = field_873_j * ((double)f * ((double)field_881_b.nextFloat() + 0.32800000000000001D));
                    double d2 = (double)field_881_b.nextFloat() * 2D * 3.1415899999999999D;
                    int k1 = (int)(d1 * Math.sin(d2) + (double)field_879_d[0] + d);
                    int l1 = (int)(d1 * Math.cos(d2) + (double)field_879_d[2] + d);
                    int ai1[] = {
                        k1, j, l1
                    };
                    int ai2[] = {
                        k1, j + field_869_n, l1
                    };
                    if(func_524_a(ai1, ai2) != -1)
                    {
                        continue;
                    }
                    int ai3[] = {
                        field_879_d[0], field_879_d[1], field_879_d[2]
                    };
                    double d3 = Math.sqrt(Math.pow(Math.abs(field_879_d[0] - ai1[0]), 2D) + Math.pow(Math.abs(field_879_d[2] - ai1[2]), 2D));
                    double d4 = d3 * field_874_i;
                    if((double)ai1[1] - d4 > (double)l)
                    {
                        ai3[1] = l;
                    } else
                    {
                        ai3[1] = (int)((double)ai1[1] - d4);
                    }
                    if(func_524_a(ai3, ai1) == -1)
                    {
                        ai[k][0] = k1;
                        ai[k][1] = j;
                        ai[k][2] = l1;
                        ai[k][3] = ai3[1];
                        k++;
                    }
                }

                j--;
                i1--;
            }
        }
        field_868_o = new int[k][4];
        System.arraycopy(ai, 0, field_868_o, 0, k);
    }

    void func_523_a(int i, int j, int k, float f, byte byte0, int l)
    {
        int i1 = (int)((double)f + 0.61799999999999999D);
        byte byte1 = field_882_a[byte0];
        byte byte2 = field_882_a[byte0 + 3];
        int ai[] = {
            i, j, k
        };
        int ai1[] = {
            0, 0, 0
        };
        int j1 = -i1;
        int k1 = -i1;
        ai1[byte0] = ai[byte0];
        for(; j1 <= i1; j1++)
        {
            ai1[byte1] = ai[byte1] + j1;
            for(int l1 = -i1; l1 <= i1;)
            {
                double d = Math.sqrt(Math.pow((double)Math.abs(j1) + 0.5D, 2D) + Math.pow((double)Math.abs(l1) + 0.5D, 2D));
                if(d > (double)f)
                {
                    l1++;
                } else
                {
                    ai1[byte2] = ai[byte2] + l1;
                    int i2 = field_880_c.func_600_a(ai1[0], ai1[1], ai1[2]);
                    if(i2 != 0 && i2 != 18)
                    {
                        l1++;
                    } else
                    {
                        field_880_c.func_634_a(ai1[0], ai1[1], ai1[2], l); // place leaf
                        l1++;
                        int rel_h = ai1[1] - field_879_d[1] + 1;
                        int rel_xz = Math.max(Math.abs(ai1[0] - field_879_d[0]), Math.abs(ai1[2] - field_879_d[2]));
                        if (rel_h > MAX_LH  && rel_xz==0) {
                        	//System.out.println("max leaf height: " + rel_h);
                        	MAX_LH = rel_h;
                        }
                        if (rel_xz > MAX_LW) {
                        	//System.out.println("max leaf width: " + rel_xz);
                        	MAX_LW = rel_xz;
                        }
                        if (rel_h > this_max_leaf && rel_xz==0) {
                        	this_max_leaf = rel_h;
                        }
                    }
                }
            }

        }

    }
    static int MAX_LH = 0;
    static int MAX_LW = 0;
    static int logs_placed = 0;
    static int max_logs = 0;
    static int this_max_leaf;
    static int MIN_LH = 99;
    float func_528_a(int i)
    {
        if((double)i < (double)(float)field_878_e * 0.29999999999999999D)
        {
            return -1.618F;
        }
        float f = (float)field_878_e / 2.0F;
        float f1 = (float)field_878_e / 2.0F - (float)i;
        float f2;
        if(f1 == 0.0F)
        {
            f2 = f;
        } else
        if(Math.abs(f1) >= f)
        {
            f2 = 0.0F;
        } else
        {
            f2 = (float)Math.sqrt(Math.pow(Math.abs(f), 2D) - Math.pow(Math.abs(f1), 2D));
        }
        f2 *= 0.5F;
        return f2;
    }

    float func_526_b(int i)
    {
        if(i < 0 || i >= field_869_n)
        {
            return -1F;
        }
        return i != 0 && i != field_869_n - 1 ? 3F : 2.0F;
    }

    void func_520_a(int i, int j, int k)
    {
        int l = j;
        for(int i1 = j + field_869_n; l < i1; l++)
        {
            float f = func_526_b(l - j);
            func_523_a(i, l, k, f, (byte)1, 18);
        }

    }

    void func_522_a(int ai[], int ai1[], int i)
    {
        int ai2[] = {
            0, 0, 0
        };
        byte byte0 = 0;
        int j = 0;
        for(; byte0 < 3; byte0++)
        {
            ai2[byte0] = ai1[byte0] - ai[byte0];
            if(Math.abs(ai2[byte0]) > Math.abs(ai2[j]))
            {
                j = byte0;
            }
        }

        if(ai2[j] == 0)
        {
            return;
        }
        byte byte1 = field_882_a[j];
        byte byte2 = field_882_a[j + 3];
        byte byte3;
        if(ai2[j] > 0)
        {
            byte3 = 1;
        } else
        {
            byte3 = -1;
        }
        double d = (double)ai2[byte1] / (double)ai2[j];
        double d1 = (double)ai2[byte2] / (double)ai2[j];
        int ai3[] = {
            0, 0, 0
        };
        int k = 0;
        for(int l = ai2[j] + byte3; k != l; k += byte3)
        {
            ai3[j] = MathHelper.func_1108_b((double)(ai[j] + k) + 0.5D);
            ai3[byte1] = MathHelper.func_1108_b((double)ai[byte1] + (double)k * d + 0.5D);
            ai3[byte2] = MathHelper.func_1108_b((double)ai[byte2] + (double)k * d1 + 0.5D);
            boolean stat = field_880_c.func_634_a(ai3[0], ai3[1], ai3[2], i); // place log
            if (stat) {
            logs_placed++;
            }
            
            
        }

    }

    void func_518_b()
    {
        int i = 0;
        for(int j = field_868_o.length; i < j; i++)
        {
            int k = field_868_o[i][0];
            int l = field_868_o[i][1];
            int i1 = field_868_o[i][2];
            func_520_a(k, l, i1);
        }

    }

    boolean func_527_c(int i)
    {
        return (double)i >= (double)field_878_e * 0.20000000000000001D;
    }

    void func_529_c()
    {
        int i = field_879_d[0];
        int j = field_879_d[1];
        int k = field_879_d[1] + field_877_f;
        int l = field_879_d[2];
        int ai[] = {
            i, j, l
        };
        int ai1[] = {
            i, k, l
        };
        func_522_a(ai, ai1, 17);
        if(field_871_l == 2)
        {
            ai[0]++;
            ai1[0]++;
            func_522_a(ai, ai1, 17);
            ai[2]++;
            ai1[2]++;
            func_522_a(ai, ai1, 17);
            ai[0]--;
            ai1[0]--;
            func_522_a(ai, ai1, 17);
        }
    }

    void func_525_d()
    {
        int i = 0;
        int j = field_868_o.length;
        int ai[] = {
            field_879_d[0], field_879_d[1], field_879_d[2]
        };
        for(; i < j; i++)
        {
            int ai1[] = field_868_o[i];
            int ai2[] = {
                ai1[0], ai1[1], ai1[2]
            };
            ai[1] = ai1[3];
            int k = ai[1] - field_879_d[1];
            if(func_527_c(k))
            {
                func_522_a(ai, ai2, 17);
            }
        }

    }

    int func_524_a(int ai[], int ai1[])
    {
        int ai2[] = {
            0, 0, 0
        };
        byte byte0 = 0;
        int i = 0;
        for(; byte0 < 3; byte0++)
        {
            ai2[byte0] = ai1[byte0] - ai[byte0];
            if(Math.abs(ai2[byte0]) > Math.abs(ai2[i]))
            {
                i = byte0;
            }
        }

        if(ai2[i] == 0)
        {
            return -1;
        }
        byte byte1 = field_882_a[i];
        byte byte2 = field_882_a[i + 3];
        byte byte3;
        if(ai2[i] > 0)
        {
            byte3 = 1;
        } else
        {
            byte3 = -1;
        }
        double d = (double)ai2[byte1] / (double)ai2[i];
        double d1 = (double)ai2[byte2] / (double)ai2[i];
        int ai3[] = {
            0, 0, 0
        };
        int j = 0;
        int k = ai2[i] + byte3;
        do
        {
            if(j == k)
            {
                break;
            }
            ai3[i] = ai[i] + j;
            ai3[byte1] = (int)((double)ai[byte1] + (double)j * d);
            ai3[byte2] = (int)((double)ai[byte2] + (double)j * d1);
            int l = field_880_c.func_600_a(ai3[0], ai3[1], ai3[2]);
            if(l != 0 && l != 18)
            {
                break;
            }
            j += byte3;
        } while(true);
        if(j == k)
        {
            return -1;
        } else
        {
            return Math.abs(j);
        }
    }

    boolean func_519_e()
    {
        int ai[] = {
            field_879_d[0], field_879_d[1], field_879_d[2]
        };
        int ai1[] = {
            field_879_d[0], (field_879_d[1] + field_878_e) - 1, field_879_d[2]
        };
        int i = field_880_c.func_600_a(field_879_d[0], field_879_d[1] - 1, field_879_d[2]);
        if(i != 2 && i != 3)
        {
            return false;
        }
        int j = func_524_a(ai, ai1);
        if(j == -1)
        {
            return true;
        }
        if(j < 6)
        {
            return false;
        } else
        {
            field_878_e = j;
            return true;
        }
    }

    public void func_517_a(double d, double d1, double d2)
    {
        field_870_m = (int)(d * 12D);
        if(d > 0.5D)
        {
            field_869_n = 5;
        }
        field_873_j = d1;
        field_872_k = d2;
    }

    public boolean func_516_a(World world, Random random, int i, int j, int k)
    {
        field_880_c = world;
        long l = random.nextLong();
        //l=564;
        //System.out.println("trees essed: " + l);
        this_max_leaf = 0;
        logs_placed = 0;
        field_881_b.setSeed(l);
        field_879_d[0] = i;
        field_879_d[1] = j;
        field_879_d[2] = k;
        //field_878_e = 11;
        if(field_878_e == 0)
        {
            field_878_e = 5 + field_881_b.nextInt(field_870_m);
            if (field_878_e != 11) {
            	return false; // I decided that it can only be 11
            }
        }
        if(!func_519_e())
        {
            return false;
        } else
        {
            func_521_a();
            func_518_b();
            func_529_c();
            func_525_d();
            if (this_max_leaf < MIN_LH) {
            	MIN_LH = this_max_leaf;
            	//System.out.println("min leaf: " + MIN_LH);
            }
            if (logs_placed > max_logs) {
            	max_logs = logs_placed;
            	//System.out.println("max logs: " + logs_placed);
            }
            if (logs_placed < min_logs) {
            	min_logs = logs_placed;
            	//System.out.println("min logs: " + logs_placed);
            }
            return true;
        }
    }
static int min_logs = 99;
    static final byte field_882_a[] = {
        2, 0, 0, 1, 2, 1
    };
    Random field_881_b;
    World field_880_c;
    int field_879_d[] = { // ok
        0, 0, 0
    };
    int field_878_e;    // not ok
    int field_877_f;   // ok
    double field_876_g;//ok
    double field_875_h;//ok
    double field_874_i;//ok
    double field_873_j;//ok
    double field_872_k;//ok
    int field_871_l; // ok
    int field_870_m;    //ok
    int field_869_n;     //  ok
    int field_868_o[][]; // ok

}
