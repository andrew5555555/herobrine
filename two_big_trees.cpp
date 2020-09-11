#include <iostream>
#include <fstream>
#include <chrono>
#include <assert.h>
#include <math.h>
using namespace std;

#define ll long long int


const ll m  = 0x5DEECE66Dll;
const ll mask =  (1ll<<48)-1;

#define advance1(s) s = (s * m + 11ll) & mask
#define advance3759(s) s = (s*0x6fe85c031f25ll + 0x8f50ecff899ll)&mask
#define advance16(s) s = (s*0x6dc260740241ll + 0xd0352014d90ll)&mask
#define advance387(s) s = (s*0x5fe2bcef32b5ll + 0xb072b3bf0cbdll)&mask
#define advance774(s) s = (s*0xf8d900133f9ll + 0x5738cac2f85ell)&mask
#define advance11(s) s = (s*0x53bce7b8c655ll + 0x3bb194f24a25ll)&mask
#define advance3(s) s = (s*0xd498bd0ac4b5ll + 0xaa8544e593dll)&mask
#define advance17(s) s = (s*0xee96bd575badll + 0xc45d76fd665bll)&mask

#define regress1(s) s = (s*0xdfe05bcb1365ll + 0x615c0e462aa9ll)&mask
#define regress3(s) s = (s*0x13a1f16f099dll + 0x95756c5d2097ll)&mask
#define regress3759(s) s = (s*0x63a9985be4adll + 0xa9aa8da9bc9bll)&mask
#define advance2(s) s = (s*0xbb20b4600a69ll + 0x40942de6ball)&mask

#define getNextInt(x, s) advance1(s); x = (int)(s>>16)

// need spare longs temp1 and temp2
#define getNextLong(x, s) getNextInt(temp1, s); getNextInt(temp2, s); x = (temp1 << 32) + temp2

#define getIntBounded(x, s, n) if ((n&(-n))==n) {advance1(s); x = (int)((n*(s>>17)) >> 31);} else {do{advance1(s); bits = s>>17; val = bits%n;}while(bits-val+(n-1)<0); x=val;}

#define getBits(x, s, n) advance1(s); x = (int) (s >> (48-n));

#define setSeed(s, x) s = x^m&mask


#define MAX_TREES 9 // can change this later (performance & output is not very sensitive to this parameter)

#define x_1 9
#define z_1 14
const int x_2 = x_1 - 7;
const int z_2 = z_1 + 1;

#define TREE_SPACING 2 // TODO: I am very unsure about the rules of big tree spawning
// for now I assume a big tree cannot spawn within 2 blocks of another tree (big or small)

char table[16][16]; // table[x][z] : is a tree allowed to try to spawn here
// 0 : no
// 1 : tree_1's territory
// 2 : tree_2's territory
// ...
// 9 : I don't know (assume yes)

// coordinates are relative to tree_1 position
#define add_unseen(sz, ez, x)  for (int j = sz+z_1; j < ez+z_1; j++) { int i = x+x_1;   if ((i&15)==i && (j&15)==j ) {table[i][j]=9;}}

void init_table() {
	for (int i = 0; i < 16; ++i)
	{
		for (int j = 0; j < 16; ++j)
		{
			table[i][j] = 0;
		}
	}
	for (int i = max(0, x_1 - TREE_SPACING); i <= min(15, x_1 + TREE_SPACING); ++i)
	{
		for (int j = max(0, z_1 - TREE_SPACING); j <= min(15, z_1 + TREE_SPACING); ++j)
		{
			table[i][j] = 1;
		}
	}
	for (int i = max(0, x_2 - TREE_SPACING); i <= min(15, x_2 + TREE_SPACING); ++i)
	{
		for (int j = max(0, z_2 - TREE_SPACING); j <= min(15, z_2 + TREE_SPACING); ++j)
		{
			table[i][j] = 2;
		}
	}
	// add locations where it's unclear if there is a tree or not
	
	add_unseen(4, 14, 6);
	add_unseen(7, 14, 5);
	add_unseen(8, 14, 4);
	add_unseen(9, 14, 3);
	add_unseen(10, 14, 2);
	add_unseen(11, 14, 1);
	add_unseen(12, 14, 0);
	add_unseen(12, 14, -1);
	add_unseen(12, 14, -2);
	add_unseen(13, 14, -3);
	add_unseen(13, 14, -4);
	add_unseen(13, 14, -5);

	// near a small tree 3
	int x_3 = x_1 - 12;
	int z_3 = z_1 - 7;
	for (int i = max(0, x_3 -12 - TREE_SPACING); i <= min(15, x_3  + TREE_SPACING); ++i)
	{
		for (int j = max(0, z_3 - TREE_SPACING); j <= min(15, z_3 + TREE_SPACING); ++j)
		{
			table[i][j] = 3;
		}
	}
	// near a small tree 4
	int x_4 = x_1 - 10;
	int z_4 = z_1 - 12;
	for (int i = max(0, x_4 -12 - TREE_SPACING); i <= min(15, x_4  + TREE_SPACING); ++i)
	{
		for (int j = max(0, z_4 - TREE_SPACING); j <= min(15, z_4 + TREE_SPACING); ++j)
		{
			table[i][j] = 4;
		}
	}
	// near a small tree 5
	int x_5 = x_1 - 5;
	int z_5 = z_1 - 15;
	for (int i = max(0, x_5 -12 - TREE_SPACING); i <= min(15, x_5  + TREE_SPACING); ++i)
	{
		for (int j = max(0, z_5 - TREE_SPACING); j <= min(15, z_5 + TREE_SPACING); ++j)
		{
			table[i][j] = 5;
		}
	}

	for (int i = 15; i >= 0; --i)
	{
		for (int j = 0; j < 16; ++j)
		{
			printf("%d ", table[i][j]);
		}
		printf("\n");
	}
	printf("\n");
}

int num_found = 0;
void output_seed(ll s) {
	++num_found;
	//printf("seed found: %lld\n", s);
} 

// we get a 48-bit candidate; (it's given that this is a big tree chunk) 
void check_tree_seed(ll s) {
	ll original = s;
	int found_1 = 0;
	int found_2 = 0;
	int tree_x, tree_z;
	for (int i = 0; i < MAX_TREES; ++i) {
		getBits(tree_x, s, 4);
		getBits(tree_z, s, 4);
		advance2(s);
		if (!found_1 && tree_x == x_1 && tree_z == z_1) {
			if (found_2) {
				output_seed(original);
				return;
			}
			found_1 = 1;
		}
		else if (!found_2 && tree_x == x_2 && tree_z == z_2) {
			if (found_1) {
				output_seed(original);
				return;
			}
			found_2 = 1;
		}
		else if ((table[tree_x][tree_z]==0) || (table[tree_x][tree_z]==1 && !found_1) || (table[tree_x][tree_z]==2 && !found_2)) {
			return; // seed is eliminated
		}
		

	}
}

// we assume that random.getNextInt(10) only advanced seed once (99.99999% chance)
void do_work() {
	ll num_checked = 0;
	for (ll upper31 = 0; upper31 < (1ll<<48); upper31 += (10ll * (1ll << 31))) {
		for (ll lower17 = 0; lower17 < (1ll<<17); lower17++) {
			ll tree_seed = upper31 | lower17;
			//assert((tree_seed>>17)%10 == 0);
			check_tree_seed(tree_seed);
			num_checked++;
			if (num_checked % ((1ll<<24)) == 0)
			{
				printf("%lld / %lld [ratio = %f] [completion=%f]\n", num_found, num_checked, 10*((float)num_checked)/num_found, 100*((num_checked*10.0)/(1ll<<48)));
			}
		}
	}
}

int main(int argc, char const *argv[])
{
	init_table();
	do_work();
	return 0;
}