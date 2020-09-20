
#include "cuda_runtime.h"
#include "device_launch_parameters.h"
#include <iostream>
#include <fstream>
#include <chrono>
#include <string>
using namespace std;
#include <stdio.h>
#include <assert.h>
#include <math.h>
#define ll long long int


const ll m = 0x5DEECE66Dll;
const ll mask = (1ll << 48) - 1;

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


#define MAX_TREES 12 // can change this later (performance & output is not very sensitive to this parameter)

#define x_1 9
#define z_1 2
const int x_2 = x_1 - 7;
const int z_2 = z_1 + 1;

#define SMALL_TREE_SPACING 2
#define BIG_TREE_SPACING 7 

__device__ __managed__ unsigned long long int num_found = 0;
#define memsz 100000
__device__ __managed__ ll ret[memsz];

__device__ __managed__ char table[16][16]; // can a tree spawn here
// 0 : no
// 1 : tree_1's territory
// 2 : tree_2's territory
// 3 : joint tree_1 and tree_2
// 8 : a small tree's leaves
// 9 : I don't know (assume yes)
__device__ __managed__ int visited = 0;



__device__ void output_seed(ll s) {
	regress1(s);
	ll id = atomicAdd(&num_found, 1ull); // dw about red underline
	ret[id] = s;
}

// coordinates are relative to tree_1 position
void add_unseen(int sz, int ez, int x) {
	sz -= 2; // fudge factor for safety
	int i = x + x_1;
	for (int j = sz + z_1; j < ez + z_1; j++) {
		if ((i & 15) == i && (j & 15) == j)
		{
			table[i][j] = 9;
		}
	}
}

// fill a rectangle with a number (1 or 2 or 3)
void fill_rect(int sx, int sz, int ex, int ez, int fill) {
	assert(fill == 1 || fill == 2 || fill == 3);
	for (int i = max(0, x_1 + sx); i < min(16, x_1 + ex); i++) {
		for (int j = max(0, z_1 + sz); j < min(16, z_1 + ez); ++j)
		{
			table[i][j] = fill;
		}
	}
}

void init_table() {
	for (int i = 0; i < 16; ++i)
	{
		for (int j = 0; j < 16; ++j)
		{
			table[i][j] = 0;
		}
	}
	/*
	// big tree leaves
	fill_rect(-1, -2, 5, 10, 1);
	fill_rect(-6, -2, -1, 10, 3);
	fill_rect(-13, 0, -6, 10, 2);
	// small pieces of tree:
	fill_rect(1, -3, 4, -2, 1);
	fill_rect(5, 2, 6, 3, 1);
	fill_rect(5, 5, 6, 7, 1);
	fill_rect(-8, -1, -6, 0, 2);
	*/
	for (int i = max(0, x_1 - BIG_TREE_SPACING); i <= min(15, x_1 + BIG_TREE_SPACING); ++i)
	{
		for (int j = max(0, z_1 - BIG_TREE_SPACING); j <= min(15, z_1 + BIG_TREE_SPACING); ++j)
		{
			table[i][j] |= 1;
		}
	}
	for (int i = max(0, x_2 - BIG_TREE_SPACING); i <= min(15, x_2 + BIG_TREE_SPACING); ++i)
	{
		for (int j = max(0, z_2 - BIG_TREE_SPACING); j <= min(15, z_2 + BIG_TREE_SPACING); ++j)
		{
			table[i][j] |= 2;
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
	add_unseen(14, 14, -6);
	add_unseen(14, 14, -7);
	add_unseen(14, 14, -8);
	add_unseen(14, 14, -9);
	add_unseen(14, 14, -10);
	add_unseen(14, 14, -11);
	add_unseen(14, 14, -12);
	add_unseen(14, 14, -13);

	// near a small tree 3
	int x_3 = x_1 - 12;
	int z_3 = z_1 - 7;
	for (int i = max(0, x_3 - SMALL_TREE_SPACING); i <= min(15, x_3 + SMALL_TREE_SPACING); ++i)
	{
		for (int j = max(0, z_3 - SMALL_TREE_SPACING); j <= min(15, z_3 + SMALL_TREE_SPACING); ++j)
		{
			table[i][j] = 8;
		}
	}
	// near a small tree 4
	int x_4 = x_1 - 10;
	int z_4 = z_1 - 12;
	for (int i = max(0, x_4 - SMALL_TREE_SPACING); i <= min(15, x_4 + SMALL_TREE_SPACING); ++i)
	{
		for (int j = max(0, z_4 - SMALL_TREE_SPACING); j <= min(15, z_4 + SMALL_TREE_SPACING); ++j)
		{
			table[i][j] = 8;
		}
	}
	// near a small tree 5
	int x_5 = x_1 - 5;
	int z_5 = z_1 - 15;
	for (int i = max(0, x_5 - SMALL_TREE_SPACING); i <= min(15, x_5 + SMALL_TREE_SPACING); ++i)
	{
		for (int j = max(0, z_5 - SMALL_TREE_SPACING); j <= min(15, z_5 + SMALL_TREE_SPACING); ++j)
		{
			table[i][j] = 8;
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




int file_num = 0;
ofstream get_next_file() {
	string path("_inter21/_intermediate");
	path = path + to_string(file_num++) + ".txt";
	ofstream of(path);
	return of;
}


// todo: work this out
__device__ int is_field_878_e_ok(int field_878_e) {
	//return 1;
	return field_878_e == 11;// || field_878_e == 12;
	//return field_878_e >= 11 && field_878_e <= 12;
}

// we get a 48-bit candidate; (it's given that this is a big tree chunk) 
__device__ void check_tree_seed(ll s, char s_table[16][16]) {
	ll original = s;
	int found_1 = 0;
	int found_2 = 0;
	int tree_x, tree_z;
	int bits, val;
	ll temp1, temp2;
	ll saved_seed;
	for (int i = 0; i < MAX_TREES; ++i) {
		getBits(tree_x, s, 4);
		getBits(tree_z, s, 4);
		if (i == 0) {
			saved_seed = s; // save for tree-height check
		}
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
		else {
			char lookup = s_table[tree_x][tree_z];
			if ((lookup == 0) || (lookup == 1 && !found_1) || (lookup == 2 && !found_2) || (lookup == 3 && !found_1 && !found_2)) {
				return; // seed is eliminated
			}
		}
		if (i == 0) {
			// we do this check as late as possible because it is more expensive
			ll internal_big_tree_seed;
			getNextLong(internal_big_tree_seed, saved_seed);
			setSeed(internal_big_tree_seed, internal_big_tree_seed);
			int field_878_e;
			getIntBounded(field_878_e, internal_big_tree_seed, 12);
			if (!is_field_878_e_ok(field_878_e + 5)) {
				return; // wrong tree-size
			}
		}

	}
}

__global__ void treeKernel(ll global_id) {
	__shared__ char s_table[16][16];
	ll tid = threadIdx.x;
	s_table[tid % 16][tid / 16] = table[tid % 16][tid / 16];
	__syncthreads();
	ll bid = blockIdx.x;
	

	ll div_ten = ((global_id << 25) | (bid << 17));
	ll upper31 = mask & (10ll*div_ten); // upper 31 bits of seed are multiple of ten
	if (upper31 < div_ten) {
		return; // overflowed the 48 bits (happens on final few blocks)
	}
	ll upper39 = upper31 | (tid << 9);

	for (ll lower9 = 0; lower9 < (1ll << 9); lower9++) {
		ll seed = upper39 | lower9;
		check_tree_seed(seed, s_table);
	}
 }


#define RUN_ID 200
cudaError_t do_work() {

	
	ofstream log("big_tree_log_200.txt");
	cudaError_t cudaStatus;

	// Choose which GPU to run on, change this on a multi-GPU system.
	cudaStatus = cudaSetDevice(0);
	if (cudaStatus != cudaSuccess) {
		fprintf(stderr, "cudaSetDevice failed!  Do you have a CUDA-capable GPU installed?");
		return cudaStatus;
	}




	int threads_per_block = 256;
	int num_blocks = 256;        // can't change these without breaking code

	// Check for any errors launching the kernel
	cudaStatus = cudaGetLastError();
	if (cudaStatus != cudaSuccess) {
		fprintf(stderr, "big tree kernel launch failed: %s\n", cudaGetErrorString(cudaStatus));
		return cudaStatus;
	}
	//ll num_found = 0;
	printf("begin xyz\n");
	auto start = chrono::steady_clock::now();
	int num_written = 0;
	ll NUM_ITERS = 838861; // ceil(2^31 / 2^8 / 10)
	//NUM_ITERS = 10000;
	for (ll o = 0; o < NUM_ITERS; o ++) {
		treeKernel <<<num_blocks, threads_per_block >>> (o);
		if (o % 100 == 0) {
			ofstream fout = get_next_file();
			cudaDeviceSynchronize();
			fout << RUN_ID << endl;
			fout << x_1 << endl << z_1 << endl;
			fout << num_found << endl;
			for (int i = 0; i < num_found; i++) {
				fout << ret[i] << endl;
				num_written++;
			}
			fout.close();
			num_found = 0;
			//printf("%lld\n", o);
			auto end = chrono::steady_clock::now();
			ll time = (chrono::duration_cast<chrono::microseconds>(end - start).count());
			float eta = ((838861-o) / ((float)o)) * ((float)time) / 3600.0 / 1000000.0;
			log << "doing " << o << " time taken us =" << time << " eta (hrs) = " << eta << endl;
			log.flush();
		}
		

	}
	
	// cudaDeviceSynchronize waits for the kernel to finish, and returns
    // any errors encountered during the launch.
	cudaStatus = cudaDeviceSynchronize();
	if (cudaStatus != cudaSuccess) {
		fprintf(stderr, "cuda not sync: %s\n", cudaGetErrorString(cudaStatus));
		return cudaStatus;
	}
	auto end = chrono::steady_clock::now();
	cout << "time taken us =" << chrono::duration_cast<chrono::microseconds>(end - start).count() << endl;

	ofstream fout = get_next_file();
	fout << RUN_ID << endl;
	fout << x_1 << endl << z_1 << endl;
	fout << num_found << endl;
	for (int i = 0; i < num_found; i++) {
		fout << ret[i] << endl;
		num_written++;
	}
	fout.close();
	cout << "total seeds written=" << num_written << endl;

	

	if (cudaStatus != cudaSuccess) {
		fprintf(stderr, "cudaDeviceSynchronize returned error code %d after launching big tree kernel!\n", cudaStatus);
	}

	return cudaStatus;
}


int main()
{
	init_table();
	// Add vectors in parallel.
	cudaError_t cudaStatus = do_work();
	if (cudaStatus != cudaSuccess) {
		fprintf(stderr, "cuda failed!");
		return 1;
	}
	// cudaDeviceReset must be called before exiting in order for profiling and
	// tracing tools such as Nsight and Visual Profiler to show complete traces.
	cudaStatus = cudaDeviceReset();
	if (cudaStatus != cudaSuccess) {
		fprintf(stderr, "cudaDeviceReset failed!");
		return 1;
	}

	return 0;
}